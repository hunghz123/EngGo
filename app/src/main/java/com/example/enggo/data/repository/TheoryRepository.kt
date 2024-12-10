package com.example.enggo.data.repository

import com.example.enggo.model.course.Theory

interface TheoryRepository {
    suspend fun getTheoryByLessonId(lessonId: Int): Theory?
}