package com.example.myapplicationtest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtest.domain.GetItemTappticEntityUseCase
import com.example.myapplicationtest.domain.GetTappticEntityUseCase
import com.example.myapplicationtest.domain.TappticEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(val getItemTappticEntityUseCase: GetItemTappticEntityUseCase,val getTappticEntityUseCase: GetTappticEntityUseCase ): ViewModel() {
    private val _sharedFlowList: MutableSharedFlow<TappticState> = MutableSharedFlow<TappticState>()

    val sharedFlowList = _sharedFlowList.asSharedFlow()
    val _stateFlow: MutableSharedFlow<TappticEntity> = MutableSharedFlow<TappticEntity>(replay = 1)

    val stateFlow = _stateFlow.asSharedFlow()
    fun getData() {
        viewModelScope.launch {
            _sharedFlowList.emit(TappticState.Loading)
            getTappticEntityUseCase.invoke()
                .retryWhen { cause, attempt ->
                    if (cause is Exception && attempt < 3) {
                        delay(2000)
                        println("??????????"+cause)
                        return@retryWhen true
                    } else {
                        return@retryWhen false
                    }
                }
                .catch { _sharedFlowList.emit(TappticState.Error(it.message))
                println(it)
                }
                .collect{
                    _sharedFlowList.emit(TappticState.Success(it))
                }


        }
    }
    fun getItem(item:String){
        viewModelScope.launch {
println("5555555555$item")
            _stateFlow.emit(TappticEntity("","0"))
            getItemTappticEntityUseCase.invoke(item)
                .catch {
                    println(it.message)
                }
                .collect{
                    println("1111111111111111$it")
                    _stateFlow.emit(it)
                    println("2222222${stateFlow}")
                }


        }
    }

}