package com.example.enggo.model.course

class UnitData (
    val course_id: Int,
    val unit_id: Int,
    val unit_name: String,
    val lessons: List<Lesson>
) {
    override fun toString(): String {
        return "UnitData(course_id=$course_id, unit_id=$unit_id, unit_name='$unit_name', lessons=$lessons)"
    }
}