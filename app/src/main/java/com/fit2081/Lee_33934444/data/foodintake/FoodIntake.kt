package com.fit2081.Lee_33934444.data.foodintake

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foodIntake")

data class FoodIntake(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userID: String?,
    val fruits: Boolean = false,
    val redMeat: Boolean = false,
    val fish: Boolean = false,
    val vegetables: Boolean = false,
    val seafood: Boolean = false,
    val eggs: Boolean = false,
    val grains: Boolean = false,
    val poultry: Boolean = false,
    val nutsSeeds: Boolean = false,
    val persona: String? = null,
    val time1: String? = null,
    val time2: String? = null,
    val time3: String? = null,
)