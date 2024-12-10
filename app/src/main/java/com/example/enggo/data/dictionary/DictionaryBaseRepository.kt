package com.example.enggo.data.dictionary

import kotlinx.coroutines.flow.Flow

interface DictionaryBaseRepository {
    fun search(word: String): Flow<List<DictionaryEntity>>

    fun prefixMatch(word: String): Flow<List<DictionaryEntity>>
}