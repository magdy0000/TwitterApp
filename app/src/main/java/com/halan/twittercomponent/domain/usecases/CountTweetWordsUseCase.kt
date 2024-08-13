package com.halan.twittercomponent.domain.usecases

import javax.inject.Inject

class CountTweetWordsUseCase @Inject constructor() {

     fun remainingTweetCharacters(tweet: String): Int {
        val maxCharacters = 280
        val usedCharacters = countTweetCharacters(tweet)
        val remaining = maxCharacters - usedCharacters
        return remaining
    }

    private fun countTweetCharacters(tweet: String): Int {
        var characterCount = 0
        val words = tweet.split("\\s+".toRegex())

        for (word in words) {
            if (word.startsWith("http://") || word.startsWith("https://")) {
                // Treat URLs as 23 characters
                characterCount += 23
            } else {
                word.forEach { char ->
                    when {
                        isCJKCharacter(char) -> characterCount += 2 // CJK characters count as 2
                        else -> characterCount += Character.charCount(char.code)
                    }
                }
            }
        }
        return characterCount
    }

    private fun isCJKCharacter(char: Char): Boolean {
        val codePoint = char.code
        return (codePoint in 0x4E00..0x9FFF) ||
                (codePoint in 0x3400..0x4DBF) ||
                (codePoint in 0x20000..0x2A6DF) ||
                (codePoint in 0x2A700..0x2B73F) ||
                (codePoint in 0x2B740..0x2B81F) ||
                (codePoint in 0x2B820..0x2CEAF)
    }


}