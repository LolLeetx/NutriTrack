package com.fit2081.Lee_33934444.data.nutricoachtips

import android.R.attr.text
import android.R.id.content
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.Lee_33934444.BuildConfig
import com.fit2081.Lee_33934444.data.foodintake.FoodIntake
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeViewModel
import com.fit2081.Lee_33934444.data.network.FruitsResponse
import com.fit2081.Lee_33934444.views.UiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NutriCoachViewModel(context: Context) : ViewModel(){


    private val nutriCoachTipsRepo = NutriCoachTipsRepository(context)

    var inputFruit by mutableStateOf("")
        private set

    fun updateInputFruit(newInput: String) {
        inputFruit = newInput
    }

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun sendPrompt(
        prevResult:String, foodIntake: FoodIntake
    ) {

        val prompt = """Generate a short encouraging message which different with "$prevResult" 
            to help someone improve their fruit intake and also according to this foodIntake:
            fruits: ${foodIntake.fruits},
            redMeat: ${foodIntake.redMeat},
            fish: ${foodIntake.fish},
            vegetables: ${foodIntake.vegetables},
            seafood: ${foodIntake.seafood},
            eggs: ${foodIntake.eggs},
            grains: ${foodIntake.grains},
            poultry: ${foodIntake.poultry},
            nutsSeeds: ${foodIntake.nutsSeeds},
            Also maybe add some emojis. 
            Make it around 50 words.""".trimIndent()
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }

    fun insertTips(tip: String, userID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (tip.isNotBlank()) {
                val newTips = NutriCoachTips(tip = tip,userID = userID)
                nutriCoachTipsRepo.insert(newTips)
            }
        }
    }

    private val _allTips = MutableStateFlow<List<String>>(emptyList())
    val allTips: MutableStateFlow<List<String>> = _allTips


    fun getAllTips(userID: String) {
        viewModelScope.launch{
            nutriCoachTipsRepo.getAllTips(userID).collect { tips ->
                _allTips.value = tips
            }
        }
    }

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog


    fun showDialog() {
        _showDialog.value = true
    }

    fun hideDialog() {
        _showDialog.value = false
    }

    class NutriCoachViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NutriCoachViewModel(context) as T
    }
}