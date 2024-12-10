package com.example.enggo.model.course

data class Lesson(
    val lesson_id: Int,
    val lesson_name: String,
    val unit_id: Int,
    val has_theory: Boolean,
    val has_exercise: Boolean,
)