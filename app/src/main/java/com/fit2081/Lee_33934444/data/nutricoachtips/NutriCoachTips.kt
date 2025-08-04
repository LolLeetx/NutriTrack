package com.fit2081.Lee_33934444.data.nutricoachtips

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutriCoachTips")

data class NutriCoachTips(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userID : String,
    val tip: String
)