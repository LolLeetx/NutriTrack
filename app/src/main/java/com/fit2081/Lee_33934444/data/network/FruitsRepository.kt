package com.fit2081.Lee_33934444.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class FruitsRepository() {
    private val apiService = APIService.create()

    suspend fun getFruitInfo(fruitName: String): Result<FruitsResponse> {
        return try{
            val response = withContext(Dispatchers.IO) {
                apiService.getFruitInfo(fruitName)
            }
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}