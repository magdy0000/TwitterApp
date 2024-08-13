package com.halan.twittercomponent.di

import com.halan.twittercomponent.BuildConfig
import com.halan.twittercomponent.data.network.ApiCalls
import com.halan.twittercomponent.data.network.OAuth1Interceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getRetrofit () : Retrofit{

        val oauthInterceptor = OAuth1Interceptor(BuildConfig.CONSUMER_KEY,BuildConfig.CONSUMER_SECRET
            ,BuildConfig.ACCESS_TOKEN , BuildConfig.TOKEN_SECRET)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(oauthInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    @Singleton
    @Provides
    fun getApiCall (retrofit: Retrofit) : ApiCalls {
        return retrofit.create(ApiCalls::class.java)
    }



}