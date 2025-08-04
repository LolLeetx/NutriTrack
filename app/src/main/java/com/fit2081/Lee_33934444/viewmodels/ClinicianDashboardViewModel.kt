package com.fit2081.Lee_33934444.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.Lee_33934444.BuildConfig
import com.fit2081.Lee_33934444.dataclass.DataPattern
import com.fit2081.Lee_33934444.views.UiState
import com.fit2081.Lee_33934444.views.UiState_DataPattern
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClinicianDashboardViewModel : ViewModel() {

    companion object{
         private const val ATTR = "Sex,HEIFAtotalscoreMale,HEIFAtotalscoreFemale,DiscretionaryHEIFAscoreMale,DiscretionaryHEIFAscoreFemale,Discretionaryservesize,VegetablesHEIFAscoreMale,VegetablesHEIFAscoreFemale,Vegetableswithlegumesallocatedservesize,LegumesallocatedVegetables,Vegetablesvariationsscore,VegetablesCruciferous,VegetablesTuberandbulb,VegetablesOther,Legumes,VegetablesGreen,VegetablesRedandorange,FruitHEIFAscoreMale,FruitHEIFAscoreFemale,Fruitservesize,Fruitvariationsscore,FruitPome,FruitTropicalandsubtropical,FruitBerry,FruitStone,FruitCitrus,FruitOther,GrainsandcerealsHEIFAscoreMale,GrainsandcerealsHEIFAscoreFemale,Grainsandcerealsservesize,GrainsandcerealsNonwholegrains,WholegrainsHEIFAscoreMale,WholegrainsHEIFAscoreFemale,Wholegrainsservesize,MeatandalternativesHEIFAscoreMale,MeatandalternativesHEIFAscoreFemale,Meatandalternativeswithlegumesallocatedservesize,LegumesallocatedMeatandalternatives,DairyandalternativesHEIFAscoreMale,DairyandalternativesHEIFAscoreFemale,Dairyandalternativesservesize,SodiumHEIFAscoreMale,SodiumHEIFAscoreFemale,Sodiummgmilligrams,AlcoholHEIFAscoreMale,AlcoholHEIFAscoreFemale,Alcoholstandarddrinks,WaterHEIFAscoreMale,WaterHEIFAscoreFemale,Water,WaterTotalmL,BeverageTotalmL,SugarHEIFAscoreMale,SugarHEIFAscoreFemale,Sugar,SaturatedFatHEIFAscoreMale,SaturatedFatHEIFAscoreFemale,SaturatedFat,UnsaturatedFatHEIFAscoreMale,UnsaturatedFatHEIFAscoreFemale,UnsaturatedFatservesize"
    }



    val gson = Gson()

    private val _uiState: MutableStateFlow<UiState_DataPattern> =
        MutableStateFlow(UiState_DataPattern.Initial)
    val uiState: StateFlow<UiState_DataPattern> =
        _uiState.asStateFlow()


    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun getPattern(rawData: String) {
        val prompt = """
        Find 3 patterns in the following data with the following attributes: $ATTR 
        The data is in the following list format: $rawData
        Note each value in each item of the list corresponds to the attributes but there are attributes i.e. ....HEIFAScoreMale and ....HEIFAScoreFemale, one of it is valid which the valid one is following the sex attribute.
        Lastly convert the 3 patterns into this json format and just respond with this json format:
        {
          "pattern1Key": "...",
          "pattern1info": "...",
          "pattern2Key": "...",
          "pattern2info": "...",
          "pattern3Key": "...",
          "pattern3info": "..."
        }
        patternkey means the key points of the pattern (within 10 words)
        patterninfo means the detailed information of the pattern (around 40 words)
        """.trimIndent()

        _uiState.value = UiState_DataPattern.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(prompt)
                    }
                )


                response.text?.let { outputContent ->
                    val cleanedJson = outputContent
                        .replace("```json", "")
                        .replace("```", "")
                        .trim()

                    val convertedPattern: DataPattern = Gson().fromJson(cleanedJson, DataPattern::class.java)
                    Log.d("Converted Pattern", "Converted Pattern: $convertedPattern")
                    _uiState.value = UiState_DataPattern.Success(convertedPattern)
                }
            } catch (e: Exception) {
                _uiState.value = UiState_DataPattern.Error(e.localizedMessage ?: "")
            }
        }
    }






}