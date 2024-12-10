package com.example.enggo.model.dictionary

import com.google.gson.annotations.SerializedName

data class Meaning(
    @SerializedName("def")
    val def: String,

    @SerializedName("example")
    val example: String?,

    @SerializedName("speech_part")
    val speechPart: String,

    @SerializedName("synonyms")
    val synonyms: List<String>?,

    @SerializedName("labels")
    val labels: List<Label>?,
)

data class Label(
    @SerializedName("name")
    val name: String,
)