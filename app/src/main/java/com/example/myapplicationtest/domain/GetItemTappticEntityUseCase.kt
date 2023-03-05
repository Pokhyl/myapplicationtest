package com.example.myapplicationtest.domain

import com.example.myapplicationtest.data.api.TappticApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemTappticEntityUseCase @Inject constructor(val tappticApi: TappticApi) {
    fun invoke(item: String): Flow<TappticEntity> {
      return  tappticApi.getItem(item)
    }
}