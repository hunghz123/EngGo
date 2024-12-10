package com.example.enggo.data.service

import android.util.Log
import com.example.enggo.data.repository.TheoryRepository
import com.example.enggo.model.UserData
import com.example.enggo.model.course.Theory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TheoryService (private val firestore: FirebaseFirestore) : TheoryRepository {
    override suspend fun getTheoryByLessonId(lessonId: Int): Theory? {
        return try {
            val querySnapshot = firestore.collection("theory")
                .whereEqualTo("lesson_id", lessonId)
                .get()
                .await()

            querySnapshot.documents.firstOrNull()?.let { document ->
                val content = document.getString("content") ?: ""

                val formattedContent = content.replace("\\n", "\n").replace("\\t", "\t")
                Theory(
                    theory_id = document.getLong("theory_id")?.toInt() ?: 0,
                    lesson_id = document.getLong("lesson_id")?.toInt() ?: 0,
                    content = formattedContent//document.getString("content") ?: ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}