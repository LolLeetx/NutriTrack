package com.fit2081.Lee_33934444.data.nutricoachtips

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fit2081.Lee_33934444.data.foodintake.FoodIntake
import kotlinx.coroutines.flow.Flow

@Dao
interface NutriCoachTipsDao{
    @Insert
    fun insert(nutriCoachTips: NutriCoachTips)

    @Query("SELECT tip FROM NutriCoachTips WHERE userID = :userID")
    fun getAllTips(userID:String): Flow<List<String>>

}