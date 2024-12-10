package com.example.enggo.data.dictionary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.enggo.model.dictionary.Meaning
import com.example.enggo.model.dictionary.WordModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "a_table")
data class DictionaryEntity(
    @ColumnInfo(name = "meanings")
    val meanings: String?,
    @ColumnInfo(name = "word")
    val word: String,
    @PrimaryKey
    @ColumnInfo(name = "wordsetId")
    val wordsetId: String
) {
    fun toWordModel(): WordModel {
        val type = object : TypeToken<List<Meaning>>() {}.type
        val meaningList = Gson().fromJson<List<Meaning>>(meanings, type)
        return WordModel(meaningList, word, wordsetId)
    }
}