package com.example.myapplicationtest.domain

import com.example.myapplicationtest.data.api.TappticApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTappticEntityUseCase @Inject constructor(var tappticApi: TappticApi) {
    fun invoke(): Flow<List<TappticEntity>>{
        return tappticApi.getList()

    }
}