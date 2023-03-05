package com.example.myapplicationtest.presentation

import com.example.myapplicationtest.domain.TappticEntity


sealed class TappticState {
    class Success(var list: List<TappticEntity>): TappticState()
    class Error(var error: String?): TappticState()
    object Loading: TappticState()

}