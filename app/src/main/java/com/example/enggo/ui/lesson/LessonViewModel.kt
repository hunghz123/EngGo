package com.example.enggo.ui.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.enggo.data.service.ExerciseService
import com.example.enggo.data.service.TheoryService
import com.example.enggo.data.service.UnitDataService
import com.example.enggo.model.course.Exercise
import com.example.enggo.ui.unit.UnitListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LessonViewModel(
    private val exerciseService: ExerciseService,
    private val lessonId: Int,
) : ViewModel() {
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    init {
        fetchExercises()
    }

    private fun fetchExercises() {
        viewModelScope.launch {
            try {
                val exerciseList = exerciseService.getExerciseByLessonId(lessonId)

                _exercises.value = exerciseList
            } catch (e: Exception) {
                _exercises.value = emptyList()
                e.printStackTrace()
            }
        }
    }
}

class LessonViewModelFactory( private val exerciseService: ExerciseService, private val lessonId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LessonViewModel(exerciseService, lessonId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}