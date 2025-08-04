package com.fit2081.Lee_33934444.data.nutricoachtips

import android.content.Context
import com.fit2081.Lee_33934444.data.NutriTrackDatabase
import kotlinx.coroutines.flow.Flow

class NutriCoachTipsRepository {
    var nutriCoachTipsDao: NutriCoachTipsDao

    constructor(context: Context) {
        nutriCoachTipsDao = NutriTrackDatabase.getDatabase(context).nutriCoachTipsDao()
    }
    suspend fun insert(nutriCoachTips: NutriCoachTips) {
        nutriCoachTipsDao.insert(nutriCoachTips)
    }

    fun getAllTips(userID: String): Flow<List<String>> {
        return nutriCoachTipsDao.getAllTips(userID)
    }
}