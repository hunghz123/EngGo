package com.example.enggo.model.dictionary

data class WordState (
    val wordModel: WordModel? = null,
    var isLoading: Boolean = false,
    var isContained: Boolean = false
)