package com.halan.twittercomponent.domain.models

data class SendTweetModel(
    val `data`: ResultData
)

data class ResultData(
    val edit_history_tweet_ids: List<String>,
    val id: String,
    val text: String
)