package com.fit2081.Lee_33934444.data.network


data class FruitsResponse(
    val name: String? = null,
    val id: Int? = null,
    val family: String? = null,
    val order: String? = null,
    val genus: String? = null,
    val nutritions: Nutrition? = null
)

data class Nutrition(
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val calories: Double,
    val sugar: Double
)




