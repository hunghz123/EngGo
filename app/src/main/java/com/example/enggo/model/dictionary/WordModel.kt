package com.example.enggo.model.dictionary

data class WordModel(
    val meanings: List<Meaning>?,
    val word: String,
    val wordsetId: String
)
