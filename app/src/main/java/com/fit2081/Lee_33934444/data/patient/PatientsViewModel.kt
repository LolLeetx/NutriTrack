package com.fit2081.Lee_33934444.data.patient

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.Lee_33934444.data.AuthManager
import com.fit2081.Lee_33934444.data.foodintake.FoodIntake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class PatientsViewModel (context: Context) : ViewModel(){

    private val patientRepo = PatientsRepository(context)

    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent = _loginEvent.asSharedFlow()

    private val _registerEvent = MutableSharedFlow<String>()
    val registerEvent = _registerEvent.asSharedFlow()

    val allPatientsID: StateFlow<List<String>> = patientRepo.getAllPatientsID()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
//    val loginPatient: StateFlow<Patient> = patientRepo.getPatientByIDFlow(AuthManager.getUserID().toString())
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            Patient())

    private val _loginPatientFlow = MutableStateFlow<Patient>( Patient())
    val loginPatientFlow: StateFlow<Patient> = _loginPatientFlow

    private val sharedPref = context.getSharedPreferences("FirstRun", MODE_PRIVATE)
    private val isFirstRun = sharedPref.getBoolean("isFirstRun", true)

    private val assets = context.applicationContext.assets

    private val _averageScore = MutableStateFlow<Map<String, Double>>(emptyMap())
    val averageScore: StateFlow<Map<String, Double>> = _averageScore

    private val _rawData = MutableStateFlow<List<String>>(emptyList())
    val rawData: StateFlow<List<String>> = _rawData

    fun loadRawData() {
        viewModelScope.launch {
            patientRepo.getAllPatientsRawData().collect { rawData ->
                Log.d("loadRawData", "Raw Data: $rawData")
                _rawData.value = rawData
            }
        }
    }

    fun loadAverageScore() {
        viewModelScope.launch {
            patientRepo.getAverageScore().collect { scores ->
                Log.d("loadAverageScore", "Scores: $scores")
                _averageScore.value = scores.associate { it.sex to it.AverageScore }
            }
        }
    }

    fun loadLoginPatient() {
        viewModelScope.launch {
            patientRepo.getPatientByIDFlow(AuthManager.getUserID().toString()).collect { list ->
                Log.d("loadPatient", "Patient: $list")
                _loginPatientFlow.value = list
            }
        }
    }



    fun removeLoginPatient() {
        viewModelScope.launch {
            _loginPatientFlow.value = Patient()
        }
    }


    fun checkPatientFruitScore(patient:Patient): Boolean{
        return patient.fruitServeSize?.toFloat()!! >= 2 && patient.fruitVariationScore?.toFloat()!! >= 2
    }

    fun loadCSV() {
        viewModelScope.launch {
            if (isFirstRun) {
                sharedPref.edit() { putBoolean("isFirstRun", false) }

                try {
                    val inputStream = assets.open("data.csv")
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val maleIndex = mutableListOf<Int>()
                    val femaleIndex = mutableListOf<Int>()


                    reader.useLines { lines ->
                        lines.forEachIndexed { index, line ->
                            val score = mutableListOf<String>()
                            val values = line.split(",")
                            if (index == 0) {
                                values.forEachIndexed { index, value ->
                                    if (value.contains("scoreMale")) {
                                        maleIndex.add(index)
                                    } else if (value.contains("scoreFemale")) {
                                        femaleIndex.add(index)
                                    }
                                }
                            } else {
                                if (values[2] == "Male") {
                                    maleIndex.forEach { index ->
                                        score.add(values[index])
                                    }
                                } else if (values[2] == "Female") {
                                    femaleIndex.forEach { index ->
                                        score.add(values[index])
                                    }
                                }
                                patientRepo.insert(
                                    Patient(
                                        userID = values[1],
                                        phoneNumber = values[0],
                                        sex = values[2],
                                        vegetablesScore = score[2],
                                        fruitsScore = score[3],
                                        fruitServeSize = values[21],
                                        fruitVariationScore = values[22],
                                        wholeGrainScore = score[4],
                                        grainCerealScore = score[5],
                                        meatScore = score[6],
                                        dairyScore = score[7],
                                        waterScore = score[10],
                                        satFatsScore = score[12],
                                        unsatFatsScore = score[13],
                                        sodiumScore = score[8],
                                        sugarScore = score[11],
                                        alcoholScore = score[9],
                                        discretionaryScore = score[1],
                                        totalScore = score[0],
                                        rawData = values.drop(2).joinToString(",")

                                    )
                                )

                            }


                        }

                    }
                } catch (e: Exception) {
                    Log.e("PatientDebug", "Error loading CSV: ${e.message}", e)
                }

            }

        }
        Log.d("loadCSV","done")
    }
    fun isAuth(password: String, selectedPatientID: String) {
        viewModelScope.launch {
            val aPatient = patientRepo.getPatientByID(selectedPatientID)
            if (password == aPatient.password) {
                _loginEvent.emit(true)
            } else {
                _loginEvent.emit(false)
            }
        }
    }

    fun registerPatient(patientID: String,
                        firstName: String,
                        lastName: String,
                        phoneNumber: String,
                        password: String,
                        confirmPassword: String)
    {
        viewModelScope.launch {
            if (phoneNumber.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty()) {
                val patient = patientRepo.getPatientByID(patientID)
                Log.d("PatientDebug","Patient ID: $patientID, Phone Number: $phoneNumber")
                Log.d("PatientDebug","$patient")

                if (patient.password != null){
                    _registerEvent.emit("patient_exists")
                }

                else if (patient.phoneNumber == phoneNumber && patient.userID == patientID) {
                    val updatedPatient = patient.copy(
                        fName = firstName,
                        lName = lastName,
                        phoneNumber = phoneNumber,
                        password = password
                    )

                    patientRepo.update(updatedPatient)
                    _registerEvent.emit("success")
                }
                else if (patient.phoneNumber != phoneNumber){
                    _registerEvent.emit("phone_num_not_match")
                }

            } else if (password != confirmPassword) {
                _registerEvent.emit("password_not_match")
            }
            else {
                _registerEvent.emit("empty_fields")
            }
        }
    }

    class PatientsViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PatientsViewModel(context) as T
    }

}