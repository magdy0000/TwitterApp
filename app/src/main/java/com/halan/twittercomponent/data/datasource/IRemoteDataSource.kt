package com.halan.twittercomponent.data.datasource

import com.halan.twittercomponent.domain.models.SendTweetModel
import com.magdy.poststask.domain.utils.Resource
import retrofit2.http.Field

interface IRemoteDataSource {

    suspend fun sendTweet(text : String ) :Resource<SendTweetModel>
}