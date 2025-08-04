package com.fit2081.Lee_33934444.data.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Dao
interface PatientDao {
    @Insert
    suspend fun insert(patient: Patient)


    @Query ("SELECT userID FROM patients ORDER BY id ASC")
    fun getAllPatientsID(): Flow<List<String>>

    @Query("SELECT * FROM patients WHERE userID = :patientID")
    suspend fun getPatientByID(patientID: String): Patient

    @Query("SELECT * FROM patients WHERE userID = :patientID")
    fun getPatientByIDFlow(patientID: String): Flow<Patient>

    @Query("SELECT sex, ROUND(AVG(totalScore), 3) AS AverageScore FROM patients GROUP BY sex")
    fun getAverageScore(): Flow<List<AverageScore>>

    @Query ("SELECT rawData FROM patients ORDER BY id ASC")
    fun getAllPatientsRawData(): Flow<List<String>>

    @Update
    suspend fun update(patient: Patient)

}
