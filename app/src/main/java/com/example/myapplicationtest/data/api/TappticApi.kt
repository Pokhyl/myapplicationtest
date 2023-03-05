package com.example.myapplicationtest.data.api

import com.example.myapplicationtest.domain.TappticEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query


interface TappticApi {
   @GET("json.php")

     fun getList(): Flow<List<TappticEntity>>
     @GET("json.php")
     fun getItem(@Query("name")id:String)

}