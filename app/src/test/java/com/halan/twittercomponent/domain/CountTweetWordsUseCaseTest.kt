package com.halan.twittercomponent.domain

import com.halan.twittercomponent.domain.usecases.CountTweetWordsUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CountTweetWordsUseCaseTest {

    private val countTweetWordsUseCase = CountTweetWordsUseCase()

    @Test
    fun `test tweet with regular text`() {
        val tweet = "This is a regular tweet."
        val remainingCharacters = countTweetWordsUseCase.remainingTweetCharacters(tweet)
        assertEquals(260, remainingCharacters) //280 - 20 chars
    }

    @Test
    fun `test tweet with URL`() {
        val tweet = "Check this out http://example.com"
        val remainingCharacters = countTweetWordsUseCase.remainingTweetCharacters(tweet)
        assertEquals(245, remainingCharacters) // 280 - 35 (12 characters + 23 for URL)
    }

    @Test
    fun `test tweet with CJK characters`() {
        val tweet = "你好"
        val remainingCharacters = countTweetWordsUseCase.remainingTweetCharacters(tweet)
        assertEquals(276, remainingCharacters) // 280 - 4 (each CJK character counts as 2)
    }

    @Test
    fun `test tweet with mixed content`() {
        val tweet = "Hello 你好 http://example.com"
        val remainingCharacters = countTweetWordsUseCase.remainingTweetCharacters(tweet)
        assertEquals(248, remainingCharacters) // 280 - 33 (5 + 4 + 23)
    }

    @Test
    fun `test tweet with multiple URLs`() {
        val tweet = "Check out these sites: http://example.com and https://anotherexample.com"
        val remainingCharacters = countTweetWordsUseCase.remainingTweetCharacters(tweet)
        assertEquals(212, remainingCharacters) // 280 - 68 (19 + 23 + 3 + 23)
    }
}
