package com.example.myapplicationtest.data.di

import com.example.myapplicationtest.data.api.TappticApi
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Provides
    @Singleton
    fun createRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl( "http://dev.tapptic.com/test/")
            .build()

    }
    @Provides
    @Singleton
    fun createTappticApi(retrofit: Retrofit): TappticApi{
        return retrofit.create(TappticApi::class.java)
    }
}