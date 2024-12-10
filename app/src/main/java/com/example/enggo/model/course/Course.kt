package com.example.enggo.model.course

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


class Course (
    val course_id: Int = 0,
    val course_name: String = "",
    val course_description: String = "",
    val course_level: Int = 0,
)

// level
// 1 - elementary
// 2 - pre-intermediate
// 3 - intermediate
// 4 - intermediate plus
// 5 - upper-intermediate
// 6 - advanced
// 7 - ielts