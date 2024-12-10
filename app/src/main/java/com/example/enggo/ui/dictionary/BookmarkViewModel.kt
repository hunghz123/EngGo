package com.example.enggo.ui.dictionary

import com.example.enggo.model.dictionary.WordModel
import kotlinx.coroutines.flow.MutableStateFlow

class BookmarkViewModel {
    var bookmarks = MutableStateFlow(listOf<WordModel>())
        private set
}