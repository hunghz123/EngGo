package com.example.enggo.data.dictionary

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryRepository(
    private val dictionaryDao: DictionaryDao,
) : DictionaryBaseRepository {

    override fun prefixMatch(word: String): Flow<List<DictionaryEntity>> = flow {
        if (validateChar(word)) {
            val query = """
                SELECT * FROM ${word.first()}_table WHERE word LIKE ? ORDER BY word ASC LIMIT 20
            """
            val queryObj = SimpleSQLiteQuery(query, arrayOf("${word}%"))
            emit(dictionaryDao.prefixMatch(queryObj))
        }

    }

    override fun search(word: String): Flow<List<DictionaryEntity>> = flow {
        if (validateChar(word)) {
            val query = """
                SELECT * FROM ${word.first()}_table WHERE word = ?
            """
            val queryObj = SimpleSQLiteQuery(query, arrayOf(word))
            emit(listOf(dictionaryDao.search(queryObj)))
        }
    }
}

private fun validateChar(word: String) : Boolean {
    val firstChar = word.first().lowercase().toCharArray()[0]
    val isValidLetter = Character.isLetter(firstChar)
    val inASCII = firstChar in 'a'..'z'
    return isValidLetter && inASCII
}