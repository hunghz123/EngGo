package com.example.enggo.data.dictionary.converters

import androidx.room.TypeConverter
import com.example.enggo.model.dictionary.Label
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LabelConverter {
    @TypeConverter
    fun fromList(labels: List<Label>): String {
        return Gson().toJson(labels)
    }

    @TypeConverter
    fun fromJson(jsonString: String): List<Label> {
        return Gson().fromJson(jsonString, object: TypeToken<List<Label>>() {}.type)
    }
}