package com.fit2081.Lee_33934444.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("api/fruit/{fruit}")
    suspend fun getFruitInfo(@Path("fruit") fruitName: String): FruitsResponse

    companion object {
        var BASE_URL = "https://www.fruityvice.com/"

        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}