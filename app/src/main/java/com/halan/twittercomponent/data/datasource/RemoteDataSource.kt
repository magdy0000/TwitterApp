package com.halan.twittercomponent.data.datasource

import com.halan.twittercomponent.data.models.TweetRequest
import com.halan.twittercomponent.data.network.ApiCalls
import com.halan.twittercomponent.domain.models.SendTweetModel
import com.magdy.poststask.domain.utils.Resource
import org.json.JSONObject
import javax.inject.Inject

class RemoteDataSource
@Inject constructor(val apiCalls: ApiCalls) : IRemoteDataSource {

    override suspend fun sendTweet(text: String): Resource<SendTweetModel> {
       return try {
          val data =   apiCalls.sendTweet(TweetRequest(text))
           if (data.body()!= null &&data.isSuccessful){
               Resource.success(data.body()!!)
           }else{
               val jsonObject = JSONObject(data.errorBody()?.string())
               Resource.error(Exception(jsonObject.getString("detail")))
           }
        }catch (e :Exception){
            Resource.error(e)
        }
    }
}