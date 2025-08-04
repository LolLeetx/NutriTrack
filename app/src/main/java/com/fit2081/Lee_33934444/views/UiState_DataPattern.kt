package com.fit2081.Lee_33934444.views

import com.fit2081.Lee_33934444.dataclass.DataPattern

sealed interface UiState_DataPattern {

    object Initial : UiState_DataPattern

    object Loading : UiState_DataPattern

    data class Success(val outputText: DataPattern) : UiState_DataPattern

    data class Error(val errorMessage: String) : UiState_DataPattern
}