package com.example.myapplicationtest.presentation

import com.example.myapplicationtest.domain.TappticEntity
import kotlinx.coroutines.flow.Flow


sealed class TappticState {
    class Success(flow: List<TappticEntity>): TappticState()
    class Error(error: String): TappticState()
    object Loading: TappticState()

}