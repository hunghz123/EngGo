package com.example.enggo.data.repository

import com.example.enggo.model.course.Course
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourseById(courseId: Int): Course?

    //suspend fun getAllCourses(): Flow<List<Course>>
    fun getAllCourses(): CollectionReference
}