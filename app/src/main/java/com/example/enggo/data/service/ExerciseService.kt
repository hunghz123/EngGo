package com.example.enggo.data.service

import android.util.Log
import com.example.enggo.data.repository.ExerciseRepository
import com.example.enggo.model.course.Exercise
import com.example.enggo.model.course.Lesson
import com.example.enggo.model.course.Question
import com.example.enggo.model.course.UnitData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ExerciseService (private val firestore: FirebaseFirestore) : ExerciseRepository {
    override suspend fun getExerciseByLessonId(lessonId: Int): List<Exercise> {
        val snapshot = firestore.collection("exercises")
            .whereEqualTo("lesson_id", lessonId)
            .get()
            .await()

        val exerciseList = mutableListOf<Exercise>()

        for (document in snapshot.documents) {
            Log.d("SNAPSHOT DOC", document.toString())

            val exerciseId = document.getLong("exercise_id")?.toInt() ?: 0
            //val lessonId = document.getLong("lesson_id")?.toInt() ?: 0
            val exerciseTitle = document.getString("exercise_title") ?: ""

            val exercisesSnapshot = firestore.collection("exercises")
                .document(document.id)
                .collection("questions")
                .get()
                .await()

            val questionList = exercisesSnapshot.documents.map { exerciseDoc ->
                Question(
                    exercise_id = (exerciseDoc.getLong("exercise_id")?.toInt() ?: 0),
                    question_id = (exerciseDoc.getLong("question_id")?.toInt() ?: 0),
                    question = exerciseDoc.getString("question") ?: "",
                    answer = exerciseDoc.getString("answer") ?: "",
                )
            }

            val exercise = Exercise(exercise_id = exerciseId, lesson_id = lessonId, exercise_title = exerciseTitle, questions = questionList)
            exerciseList.add(exercise)
        }
        return exerciseList
    }
}