package com.example.enggo.data.dictionary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.enggo.data.dictionary.converters.MeaningConverter
import androidx.room.RoomDatabase
import com.example.enggo.data.dictionary.converters.LabelConverter
import com.example.enggo.data.dictionary.converters.SynonymConverter

@TypeConverters(value = [MeaningConverter::class, SynonymConverter::class, LabelConverter::class])
@Database(entities = [DictionaryEntity::class], exportSchema = true, version = 2)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract val dictionaryDao: DictionaryDao
}