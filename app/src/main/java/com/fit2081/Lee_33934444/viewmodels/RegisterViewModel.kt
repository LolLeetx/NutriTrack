package com.fit2081.Lee_33934444.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.fit2081.Lee_33934444.data.patient.Patient
import com.fit2081.Lee_33934444.data.patient.PatientsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.sequences.forEach

class RegisterViewModel(application: Application) : AndroidViewModel(application){


    var phoneNumber by mutableStateOf("")
        private set

    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var selectedPatientID by mutableStateOf("")
        private set

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber = newPhoneNumber
    }

    fun updateFirstName(newFirstName: String) {
        firstName = newFirstName
    }

    fun updateLastName(newLastName: String) {
        lastName = newLastName
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
    }

    fun updateSelectedPatientID(patientID: String) {
        selectedPatientID = patientID
    }

}