package com.example.myapplicationtest.presentation.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtest.data.api.TappticApi
import com.example.myapplicationtest.domain.GetItemTappticEntityUseCase
import com.example.myapplicationtest.presentation.TappticState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemFragmentViewModel @Inject constructor(val getItemTappticEntityUseCase: GetItemTappticEntityUseCase): ViewModel() {
    private val _sharedFlow: MutableSharedFlow<TappticState> = MutableSharedFlow<TappticState>()

    val sharedFlow = _sharedFlow.asSharedFlow()
    fun getItem(item:String){
        viewModelScope.launch {
            _sharedFlow.emit(TappticState.Loading)
            getItemTappticEntityUseCase.invoke(item)
                .catch { _sharedFlow.emit(TappticState.Error(it.message)) }
                .collect{
                    _sharedFlow.emit(TappticState.SuccessItem(it))
                }


        }
    }

}