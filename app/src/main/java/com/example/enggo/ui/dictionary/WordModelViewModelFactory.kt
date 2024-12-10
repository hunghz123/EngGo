package com.example.enggo.ui.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enggo.data.dictionary.DictionaryBaseRepository

class WordModelViewModelFactory(
    private val dictRepository: DictionaryBaseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordModelViewModel::class.java)) {
            return WordModelViewModel(dictRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}