package com.example.myapplicationtest.data.di
import com.example.myapplicationtest.data.api.TappticApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory
import java.io.IOException
import javax.inject.Singleton


import okhttp3.OkHttpClient.Builder

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Provides
    @Singleton
    fun createRetrofit(okHttp: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .client(okHttp)
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
    @Provides
    @Singleton
    fun okHttpCreate(): OkHttpClient {
        return  OkHttpClient.Builder()
            .build()
    }
}