package com.fit2081.Lee_33934444.data.foodintake



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


@Dao
interface FoodIntakeDao{
    @Insert
    suspend fun init(foodIntake: FoodIntake)

    @Query("SELECT * FROM foodIntake WHERE userID = :userID")
    fun getFoodIntakeFlowByUserID(userID: String): Flow<FoodIntake>

    @Query("SELECT * FROM foodIntake WHERE userID = :userID")
    suspend fun getFoodIntakeByUserID(userID: String): FoodIntake

    @Update
    suspend fun update(foodIntake: FoodIntake)
}