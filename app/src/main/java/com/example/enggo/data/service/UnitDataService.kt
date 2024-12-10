package com.example.enggo.data.service

import android.util.Log
import com.example.enggo.data.repository.UnitDataRepository
import com.example.enggo.model.course.Lesson
import com.example.enggo.model.course.UnitData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class UnitDataService (private val firestore: FirebaseFirestore) : UnitDataRepository {
    override suspend fun getUnitDataByCourseId(courseId: Int): List<UnitData> {
        val snapshot = firestore.collection("unit")
            .whereEqualTo("course_id", courseId)
            .get()
            .await()

        val unitDataList = mutableListOf<UnitData>()

        for (document in snapshot.documents) {
            Log.d("SNAPSHOT DOC", document.toString())

            //val courseId = document.getLong("course_id")?.toInt() ?: 0
            val unitId = document.getLong("unit_id")?.toInt() ?: 0
            val unitName = document.getString("unit_name") ?: ""

            val lessonsSnapshot = firestore.collection("unit")
                .document(document.id)
                .collection("lessons")
                .get()
                .await()
            Log.d("LESSONS SNAPSHOT", lessonsSnapshot.toString())

            val lessonList = lessonsSnapshot.documents.map { lessonDoc ->
                Lesson(
                    lesson_id = (lessonDoc.getLong("lesson_id")?.toInt() ?: 0),
                    lesson_name = lessonDoc.getString("lesson_name") ?: "",
                    unit_id = (lessonDoc.getLong("unit_id")?.toInt() ?: 0),
                    has_theory = lessonDoc.getBoolean("has_theory") ?: false,
                    has_exercise = lessonDoc.getBoolean("has_exercise") ?: false
                )
            }

            val unitData = UnitData(course_id = courseId, unit_id = unitId, unit_name = unitName, lessons = lessonList)
            unitDataList.add(unitData)
        }
        unitDataList.forEach { unitData ->
            Log.d("CHECK FIREBASE", unitData.toString())
        }
        return unitDataList
    }

}