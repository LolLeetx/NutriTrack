package com.fit2081.Lee_33934444.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fit2081.Lee_33934444.data.foodintake.FoodIntake
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeDao
import com.fit2081.Lee_33934444.data.nutricoachtips.NutriCoachTips
import com.fit2081.Lee_33934444.data.nutricoachtips.NutriCoachTipsDao
import com.fit2081.Lee_33934444.data.patient.Patient
import com.fit2081.Lee_33934444.data.patient.PatientDao


@Database(entities = [Patient::class, FoodIntake::class ,NutriCoachTips::class], version = 2, exportSchema = false)
// this is a room database
abstract class NutriTrackDatabase : RoomDatabase() {


    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun nutriCoachTipsDao(): NutriCoachTipsDao

    companion object {
        @Volatile
        private var INSTANCE: NutriTrackDatabase? = null

        fun getDatabase(context: Context): NutriTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NutriTrackDatabase::class.java,
                    "nutritrack_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}