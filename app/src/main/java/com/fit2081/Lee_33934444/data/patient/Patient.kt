package com.fit2081.Lee_33934444.data.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val password : String? = null,
    val userID: String? = null,
    val phoneNumber: String? = null,
    val fName: String? = null,
    val lName: String? = null,
    val sex: String? = null,
    val vegetablesScore: String? = null,
    val fruitsScore: String? = null,
    val wholeGrainScore: String? = null,
    val grainCerealScore: String? = null,
    val meatScore: String? = null,
    val dairyScore: String? = null,
    val waterScore: String? = null,
    val satFatsScore: String? = null,
    val unsatFatsScore: String? = null,
    val sodiumScore: String? = null,
    val sugarScore: String? = null,
    val alcoholScore: String? = null,
    val discretionaryScore: String? = null,
    val totalScore: String? = null,
    val fruitServeSize: String? = null,
    val fruitVariationScore: String? = null,
    val rawData: String? = null

)

