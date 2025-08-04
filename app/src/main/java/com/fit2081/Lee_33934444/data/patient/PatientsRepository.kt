package com.fit2081.Lee_33934444.data.patient

import android.content.Context
import com.fit2081.Lee_33934444.data.NutriTrackDatabase
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.text.insert

class PatientsRepository {

    var patientDao: PatientDao

    constructor(context: Context) {
        patientDao = NutriTrackDatabase.getDatabase(context).patientDao()
    }

    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    fun getAllPatientsID(): Flow<List<String>> {
        return patientDao.getAllPatientsID()
    }

    suspend fun getPatientByID(patientID: String): Patient {
        return patientDao.getPatientByID(patientID)
    }

    fun getPatientByIDFlow(patientID: String): Flow<Patient> {
        return patientDao.getPatientByIDFlow(patientID)
    }

    fun getAverageScore(): Flow<List<AverageScore>> {
        return patientDao.getAverageScore()
    }

    suspend fun update(patient: Patient){
        patientDao.update(patient)
    }

    fun getAllPatientsRawData(): Flow<List<String>> {
        return patientDao.getAllPatientsRawData()
    }

}