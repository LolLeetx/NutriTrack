package com.fit2081.Lee_33934444.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ClinicianViewModel: ViewModel() {
    companion object{
        private const val CLINICIAN_KEY = "dollar-entry-apples"
    }

    var inputKey by mutableStateOf("")
        private set

    private val _clinicianEvent = MutableSharedFlow<Boolean>()
    val clinicianEvent = _clinicianEvent.asSharedFlow()

    fun updateInputKey(newInput: String) {
        inputKey = newInput
    }

    fun validateKey(inputKey: String) {
        viewModelScope.launch{
            if (inputKey == CLINICIAN_KEY) {
                _clinicianEvent.emit(true)
            } else {
                _clinicianEvent.emit(false)
            }
        }
    }


}