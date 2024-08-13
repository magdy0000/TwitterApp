package com.halan.twittercomponent.di

import com.halan.twittercomponent.data.Repository
import com.halan.twittercomponent.data.datasource.IRemoteDataSource
import com.halan.twittercomponent.data.datasource.RemoteDataSource
import com.halan.twittercomponent.data.network.ApiCalls
import com.halan.twittercomponent.domain.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun getRemoteDataSource(apiCalls: ApiCalls): IRemoteDataSource {
        return RemoteDataSource(apiCalls)
    }

    @Provides
    fun getRepository(
        iRemoteDataSource: IRemoteDataSource
    ): IRepository {
        return Repository(iRemoteDataSource)
    }

}