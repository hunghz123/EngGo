package com.example.enggo.ui.unit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.enggo.data.service.UnitDataService
import com.example.enggo.model.course.UnitData
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UnitListViewModel (
    private val unitDataService: UnitDataService,
    private val courseId: Int,
) : ViewModel() {
    private val _units = MutableStateFlow<List<UnitData>>(emptyList())
    val units: StateFlow<List<UnitData>> = _units

    private var unitListener: ListenerRegistration? = null

    init {
        fetchUnits()
    }

    private fun fetchUnits() {
        //unitListener?.remove()
        viewModelScope.launch {
            try {
                val unitDataList = unitDataService.getUnitDataByCourseId(courseId)

                _units.value = unitDataList
            } catch (e: Exception) {
                _units.value = emptyList()
                e.printStackTrace()
            }
        }


    }
}

class UnitDataViewModelFactory(private val unitDataService: UnitDataService, private val courseId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UnitListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UnitListViewModel(unitDataService, courseId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}