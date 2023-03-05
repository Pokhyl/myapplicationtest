package com.example.myapplicationtest.data.api

import com.example.myapplicationtest.domain.TappticEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET


interface TappticApi {
   @GET(" json.php")
     fun getList(): Flow<List<TappticEntity>>

}