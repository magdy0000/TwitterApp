package com.halan.twittercomponent.data

import com.halan.twittercomponent.data.datasource.IRemoteDataSource
import com.halan.twittercomponent.domain.IRepository
import com.halan.twittercomponent.domain.models.SendTweetModel
import com.magdy.poststask.domain.utils.Resource
import javax.inject.Inject

class Repository @Inject constructor (val iRemoteDataSource: IRemoteDataSource) : IRepository {
    override suspend fun sendTweet(text: String): Resource<SendTweetModel> {
        return iRemoteDataSource.sendTweet(text)
    }
}