package com.fit2081.Lee_33934444.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object AuthManager {
    val _userID: MutableState<String?> = mutableStateOf(null)

    fun login(userID: String) {
        _userID.value = userID
    }

    fun logout() {
        _userID.value = null
    }

    fun getUserID(): String? {
        return _userID.value
    }
}