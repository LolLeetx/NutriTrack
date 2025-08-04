package com.fit2081.Lee_33934444.views

sealed interface UiState {

    object Initial : UiState

    object Loading : UiState

    data class Success(val outputText: String) : UiState

    data class Error(val errorMessage: String) : UiState
}