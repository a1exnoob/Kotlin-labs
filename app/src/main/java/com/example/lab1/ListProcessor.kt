package com.example.lab1

data class WordSearchResult(
    val originalText: String,
    val words: List<String>
)

object ListProcessor {
    fun findWordsWithSameStartEnd(text: String): WordSearchResult {
        val words = text.split("\\s+".toRegex())
            .map { it.trim().lowercase() }
            .filter { it.isNotEmpty() && it.first() == it.last() }

        return WordSearchResult(
            originalText = text,
            words = words
        )
    }
}