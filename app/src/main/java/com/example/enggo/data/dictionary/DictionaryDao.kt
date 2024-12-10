package com.example.enggo.data.dictionary

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface DictionaryDao {
    @RawQuery
    suspend fun prefixMatch(query: SupportSQLiteQuery): List<DictionaryEntity>

    @RawQuery
    suspend fun search(query: SupportSQLiteQuery): DictionaryEntity
}