package com.example.enggo.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.enggo.data.repository.CourseRepository
import com.example.enggo.data.service.CourseService
import com.example.enggo.model.course.Course
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class CourseUiState(
    val courseList: List<Course> = listOf()
)

class CourseViewModel(
    //val courseRepository: CourseRepository
    private val courseService: CourseService
) : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private var courseListener: ListenerRegistration? = null

    init {
        fetchCourses()
    }

//    val courseUiState: StateFlow<CourseUiState> =
//        courseRepository.getAllCourses().map { CourseUiState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = CourseUiState()
//            )

    private fun fetchCourses() {
        courseListener?.remove()
        courseListener = courseService.getAllCourses().addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                // Error Handler
                return@addSnapshotListener
            }

            querySnapshot?.let {it ->
                val fetchedCourses = it.documents.mapNotNull { document ->
                    document.toObject(Course::class.java)
                }
                _courses.value = fetchedCourses

            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        courseListener?.remove()
    }
}

class CourseViewModelFactory(private val courseService: CourseService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseViewModel(courseService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
