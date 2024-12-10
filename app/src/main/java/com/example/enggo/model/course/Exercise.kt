package com.example.enggo.model.course

data class Exercise(
    val exercise_id: Int,
    val exercise_title: String,
    val lesson_id: Int,
    val questions: List<Question>
)