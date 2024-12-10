package com.example.enggo.model.dictionary.util

import com.example.enggo.model.dictionary.WordModel

class TrieNode<Key>(var key: Key?, var parent: TrieNode<Key>?) {
    val children: HashMap<Key, TrieNode<Key>> = HashMap()
    var isValidWord = false
    var wordModel: WordModel? = null
}