package com.fit2081.Lee_33934444.data.foodintake

import android.content.Context
import com.fit2081.Lee_33934444.data.NutriTrackDatabase
import kotlinx.coroutines.flow.Flow

class FoodIntakesRepository {
    var foodIntakeDao: FoodIntakeDao

    constructor(context: Context) {
        foodIntakeDao = NutriTrackDatabase.getDatabase(context).foodIntakeDao()
    }

    suspend fun init(userID: String) {
        val foodIntake = FoodIntake(
            userID = userID
        )
        foodIntakeDao.init(foodIntake)
    }

    fun getFoodIntakeFlowByUserID(userID: String): Flow<FoodIntake> {
        return foodIntakeDao.getFoodIntakeFlowByUserID(userID)
    }

    suspend fun getFoodIntakeByUserID(userID: String): FoodIntake {
        return foodIntakeDao.getFoodIntakeByUserID(userID)
    }

    suspend fun update(foodIntake: FoodIntake) {
        foodIntakeDao.update(foodIntake)
    }
}