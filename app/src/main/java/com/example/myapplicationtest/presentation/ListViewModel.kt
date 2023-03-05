package com.example.myapplicationtest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtest.domain.GetTappticEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ListViewModel @Inject constructor(val getTappticAntityUseCase: GetTappticEntityUseCase): ViewModel() {
    private val _sharedFlow: MutableSharedFlow<TappticState> = MutableSharedFlow<TappticState>()

    val sharedFlow = _sharedFlow.asSharedFlow()

    fun getData() {
        viewModelScope.launch {
            _sharedFlow.emit(TappticState.Loading)
            getTappticAntityUseCase.invoke().collect{
                _sharedFlow.emit(TappticState.Success(it))
            }


        }
    }
}