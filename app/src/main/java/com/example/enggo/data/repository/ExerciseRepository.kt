package com.example.enggo.data.repository

import com.example.enggo.model.course.Exercise

interface ExerciseRepository {
    suspend fun getExerciseByLessonId(lessonId: Int): List<Exercise>
}