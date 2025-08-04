package com.fit2081.Lee_33934444.data.foodintake


import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.Lee_33934444.data.AuthManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodIntakeViewModel(context: Context) : ViewModel(){
    var hasInit by mutableStateOf(false)
        private set

    var fruits by mutableStateOf(false)
        private set

    var redMeat by mutableStateOf(false)
        private set

    var fish by mutableStateOf(false)
        private set

    var vegetables by mutableStateOf(false)
        private set

    var seafood by mutableStateOf(false)
        private set

    var eggs by mutableStateOf(false)
        private set

    var grains by mutableStateOf(false)
        private set

    var poultry by mutableStateOf(false)
        private set

    var nutsSeeds by mutableStateOf(false)
        private set

    var persona by mutableStateOf("")
        private set

    var time1 by mutableStateOf("")
        private set

    var time2 by mutableStateOf("")
        private set

    var time3 by mutableStateOf("")
        private set

    var modalPersona by mutableStateOf("")
        private set

    var modalPersonaImage by mutableStateOf(0)
        private set

    var modalPersonaDescription by mutableStateOf("")
        private set

    fun updateHasInit(value: Boolean) {
        hasInit = value
    }

    fun updateFruits(value: Boolean) {
        Log.d("FoodIntakeViewModel", "updateFruits called with value: $value")
        fruits = value
    }

    fun updateRedMeat(value: Boolean) {
        redMeat = value
    }

    fun updateFish(value: Boolean) {
        fish = value
    }

    fun updateVegetables(value: Boolean) {
        vegetables = value
    }

    fun updateSeafood(value: Boolean) {
        seafood = value
    }

    fun updateEggs(value: Boolean) {
        eggs = value
    }

    fun updateGrains(value: Boolean) {
        grains = value
    }

    fun updatePoultry(value: Boolean) {
        poultry = value
    }

    fun updateNutsSeeds(value: Boolean) {
        nutsSeeds = value
    }

    fun updatePersona(value: String) {
        persona = value
    }

    fun updateTime(index: Int, value: String) {
        when (index) {
            1 -> time1 = value
            2 -> time2 = value
            3 -> time3 = value
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

    fun updateModalPersona(value: String) {
        modalPersona = value
    }

    fun updateModalPersonaImage(value: Int) {
        modalPersonaImage = value
    }

    fun updateModalPersonaDescription(value: String) {
        modalPersonaDescription = value
    }

    private val _saveEvent = MutableSharedFlow<Boolean>()
    val saveEvent = _saveEvent.asSharedFlow()

    private val foodIntakesRepo = FoodIntakesRepository(context)

    val foodIntakeFlow: StateFlow<FoodIntake> = foodIntakesRepo.getFoodIntakeFlowByUserID(AuthManager.getUserID()
        .toString()).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FoodIntake(userID = null)
    )

    suspend fun init(userID:String) {
        foodIntakesRepo.init(userID)
    }


    fun updateFoodIntake(UserID: String) {
        viewModelScope.launch{
            if ((fruits || redMeat || fish || vegetables || seafood || eggs || grains || poultry || nutsSeeds)
                && persona != "" && (time1 != time2 && time1 != time3 && time2 != time3)){
                val foodIntake = foodIntakesRepo.getFoodIntakeByUserID(UserID)
                val updatedFoodIntake = foodIntake.copy(
                    fruits = fruits,
                    redMeat = redMeat,
                    fish = fish,
                    vegetables = vegetables,
                    seafood = seafood,
                    eggs = eggs,
                    grains = grains,
                    poultry = poultry,
                    nutsSeeds = nutsSeeds,
                    persona = persona,
                    time1 = time1,
                    time2 = time2,
                    time3 = time3
                )
                foodIntakesRepo.update(updatedFoodIntake)
                _saveEvent.emit(true)
            } else{
                _saveEvent.emit(false)
            }
        }

    }

    private val _foodIntakeStateFlow = MutableStateFlow<FoodIntake>(FoodIntake(userID = null))
    val foodIntakeStateFlow: StateFlow<FoodIntake> = _foodIntakeStateFlow

    fun loadFoodIntake(userID: String){
        viewModelScope.launch {
            foodIntakesRepo.getFoodIntakeFlowByUserID(userID).collect { list ->
                Log.d("FoodDebug", "Received foodIntake list: $list")
                _foodIntakeStateFlow.value = list
            }
        }
    }

    fun removeFoodIntake() {
        viewModelScope.launch {
            _foodIntakeStateFlow.value = FoodIntake(userID = null)
        }
    }

    class FoodIntakeViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FoodIntakeViewModel(context) as T
    }









}