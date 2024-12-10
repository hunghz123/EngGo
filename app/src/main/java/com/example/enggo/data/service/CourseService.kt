package com.example.enggo.data.service

import com.example.enggo.data.repository.CourseRepository
import com.example.enggo.model.course.Course
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class CourseService(private val firestore: FirebaseFirestore) : CourseRepository {
    override suspend fun getCourseById(courseId: Int): Course? {
        TODO("Not yet implemented")
    }

    override fun getAllCourses(): CollectionReference {
        return firestore.collection("course")
    }

}