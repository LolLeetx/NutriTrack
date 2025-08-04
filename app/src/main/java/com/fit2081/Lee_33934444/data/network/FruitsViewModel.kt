package com.fit2081.Lee_33934444.data.network

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeViewModel
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FruitsViewModel() : ViewModel(){

    val fruitsRepo = FruitsRepository()
    private val _fruitInfo = MutableStateFlow<FruitsResponse>(FruitsResponse())
    val fruitInfo: StateFlow<FruitsResponse> = _fruitInfo

    private val _fetchEvent = MutableSharedFlow<Boolean>()
    val fetchEvent = _fetchEvent.asSharedFlow()

    fun getFruitInfo(fruitName: String){
        viewModelScope.launch{
            val result = fruitsRepo.getFruitInfo(fruitName)
            if (result.isSuccess) {
                _fruitInfo.value = result.getOrNull() ?: FruitsResponse()
                _fetchEvent.emit(true)
            }
            else {
                _fetchEvent.emit(false)
                _fruitInfo.value = FruitsResponse()
            }
        }

    }



}
