package com.fit2081.Lee_33934444.viewmodels

import android.R.string
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.fit2081.Lee_33934444.data.AuthManager
import com.fit2081.Lee_33934444.data.NutriTrackDatabase
import com.fit2081.Lee_33934444.data.patient.Patient
import com.fit2081.Lee_33934444.data.patient.PatientsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader

class LoginViewModel(application: Application) : AndroidViewModel(application)  {


    var password by mutableStateOf("")
        private set

    var selectedPatientID by mutableStateOf("")
        private set

    fun updateSelectedPatientID(patientID : String) {
        selectedPatientID = patientID
    }

    fun updatePassword(currentPassword: String){
        password = currentPassword
    }

    fun logIn(userID: String) {
        AuthManager.login(userID)
    }

}

