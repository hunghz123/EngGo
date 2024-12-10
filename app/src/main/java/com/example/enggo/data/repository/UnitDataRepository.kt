package com.example.enggo.data.repository

import com.example.enggo.model.course.UnitData
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.StateFlow

interface UnitDataRepository {

    //suspend fun getUnitDataByCourseId(courseId: Int): StateFlow<List<UnitData>>
    suspend fun getUnitDataByCourseId(courseId: Int): List<UnitData>
}