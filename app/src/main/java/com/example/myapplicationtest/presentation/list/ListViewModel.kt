package com.example.myapplicationtest.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtest.domain.GetTappticEntityUseCase
import com.example.myapplicationtest.presentation.TappticState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ListViewModel @Inject constructor(val getTappticAntityUseCase: GetTappticEntityUseCase): ViewModel() {
    private val _sharedFlow: MutableSharedFlow<TappticState> = MutableSharedFlow<TappticState>()

    val sharedFlow = _sharedFlow.asSharedFlow()

    fun getData() {
        viewModelScope.launch {
            _sharedFlow.emit(TappticState.Loading)
            getTappticAntityUseCase.invoke()
                .retryWhen { cause, attempt ->
                    if (cause is Exception && attempt < 3) {
                        delay(2000)
                        return@retryWhen true
                    } else {
                        return@retryWhen false
                    }
                }
                .catch { _sharedFlow.emit(TappticState.Error(it.message)) }
                .collect{
                _sharedFlow.emit(TappticState.Success(it))
            }


        }
    }
}