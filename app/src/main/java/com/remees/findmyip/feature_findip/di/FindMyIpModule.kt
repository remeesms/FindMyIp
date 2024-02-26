package com.remees.findmyip.feature_findip.di

import com.remees.findmyip.feature_findip.data.remote.FindMyIpApi
import com.remees.findmyip.feature_findip.domain.GetIpInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FindMyIpModule {

    @Provides
    @Singleton
    fun provideGetIpInfoUseCase(api: FindMyIpApi): GetIpInfo {
        return GetIpInfo(api)
    }

    @Provides
    @Singleton
    fun provideFindMyIpApi(): FindMyIpApi {
        return Retrofit.Builder()
            .baseUrl(FindMyIpApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FindMyIpApi::class.java)
    }
}