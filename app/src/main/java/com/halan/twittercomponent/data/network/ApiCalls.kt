package com.halan.twittercomponent.data.network

import com.halan.twittercomponent.data.models.TweetRequest
import com.halan.twittercomponent.domain.models.SendTweetModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCalls {



    @POST("tweets")
    suspend fun sendTweet(@Body tweetRequest : TweetRequest) : Response<SendTweetModel>


}