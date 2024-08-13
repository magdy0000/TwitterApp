package com.halan.twittercomponent.domain

import com.halan.twittercomponent.domain.models.SendTweetModel
import com.magdy.poststask.domain.utils.Resource

interface IRepository {

    suspend fun sendTweet(text : String ) : Resource<SendTweetModel>

}