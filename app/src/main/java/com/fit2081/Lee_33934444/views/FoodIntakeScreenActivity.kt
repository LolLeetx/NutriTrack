package com.fit2081.Lee_33934444.views

import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.fit2081.Lee_33934444.R
import com.fit2081.Lee_33934444.data.AuthManager
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeViewModel
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel

import java.util.Calendar


//class FoodIntakeActivity : ComponentActivity() {
//    @OptIn(ExperimentalMaterial3Api::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            val foodIntakeViewModel = ViewModelProvider(
//                this,
//                FoodIntakeViewModel.FoodIntakeViewModelFactory(applicationContext)
//            ).get(FoodIntakeViewModel::class.java)
//
//            NutriTrackTheme {
//                Scaffold(
//                    topBar = {
//                        CenterAlignedTopAppBar(
//                            title = {
//                                Text(
//                                    text = "Food Intake Questionnaire",
//                                    style = TextStyle(
//                                        fontSize = 24.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                )
//                            },
//                            navigationIcon = {
//                                IconButton(
//                                    onClick = {
//
//                                    }) {
//                                    Icon(
//                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                                        contentDescription = "Localized description"
//                                    )
//                                }
//                            }
//                        )
//                    }
//
//                ) { innerPadding ->
//                    FoodIntakeScreen(
//                        modifier = Modifier.padding(innerPadding),
//                        foodIntakeViewModel = foodIntakeViewModel
//                    )
//                }
//            }
//        }
//    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodIntakeScreen(navController: NavHostController, patientsViewModel: PatientsViewModel, foodIntakeViewModel: FoodIntakeViewModel) {

    val _context = LocalContext.current

    foodIntakeViewModel.loadFoodIntake(AuthManager.getUserID().toString())

    val foodIntake by foodIntakeViewModel.foodIntakeStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        patientsViewModel.loadLoginPatient()
    }

    Log.d("auth ", "Login Patient: ${AuthManager.getUserID().toString()}")
    val loginPatient = patientsViewModel.loginPatientFlow.collectAsStateWithLifecycle().value
    Log.d("HomeScreen", "Login Patient: $loginPatient")

    LaunchedEffect(Unit) {
        foodIntakeViewModel.saveEvent.collect { event ->
            when (event) {
                true -> {
                    Toast.makeText(_context, "Save successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("Home")
                }

                false -> {
                    Toast.makeText(_context, "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val hasInit = foodIntakeViewModel.hasInit

    Log.d("foodIntake1", "FoodIntakeScreen: $foodIntake")
    LaunchedEffect(foodIntake) {
        Log.d("before has init", "$hasInit,$foodIntake")
        if (!hasInit && foodIntake.userID != null) {

            Log.d("after has init", "$hasInit")
            foodIntakeViewModel.updateFruits(foodIntake.fruits)
            Log.d("fruits", "${foodIntake.fruits}")
            foodIntakeViewModel.updateRedMeat(foodIntake.redMeat)
            foodIntakeViewModel.updateFish(foodIntake.fish)
            foodIntakeViewModel.updateVegetables(foodIntake.vegetables)
            foodIntakeViewModel.updateSeafood(foodIntake.seafood)
            foodIntakeViewModel.updateEggs(foodIntake.eggs)
            foodIntakeViewModel.updateGrains(foodIntake.grains)
            foodIntakeViewModel.updatePoultry(foodIntake.poultry)
            foodIntakeViewModel.updateNutsSeeds(foodIntake.nutsSeeds)
            foodIntakeViewModel.updatePersona(foodIntake.persona ?: "")
            foodIntakeViewModel.updateTime(1, foodIntake.time1 ?: "00:00")
            foodIntakeViewModel.updateTime(2, foodIntake.time2 ?: "00:00")
            foodIntakeViewModel.updateTime(3, foodIntake.time3 ?: "00:00")
            foodIntakeViewModel.updateHasInit(true)
        }
    }

    val fruits = foodIntakeViewModel.fruits
    val redMeat = foodIntakeViewModel.redMeat
    val fish = foodIntakeViewModel.fish
    val vegetables = foodIntakeViewModel.vegetables
    val seafood = foodIntakeViewModel.seafood
    val eggs = foodIntakeViewModel.eggs
    val grains = foodIntakeViewModel.grains
    val poultry = foodIntakeViewModel.poultry
    val nutsSeeds = foodIntakeViewModel.nutsSeeds
    val persona = foodIntakeViewModel.persona
    val time1 = foodIntakeViewModel.time1
    val time2 = foodIntakeViewModel.time2
    val time3 = foodIntakeViewModel.time3

    val showDialog by foodIntakeViewModel.showDialog.collectAsState()


    val modalPersona = foodIntakeViewModel.modalPersona
    val modalPersonaImage = foodIntakeViewModel.modalPersonaImage
    val modalPersonaDescription = foodIntakeViewModel.modalPersonaDescription


    Log.d(
        "auth",
        "FoodIntakeScreen: ${AuthManager.getUserID().toString()},${AuthManager._userID.value}"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Food Intake Questionnaire",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("login_screen")
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }

    ) { innerpadding ->
        Column(
            modifier = Modifier.padding(innerpadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Tick all the food categories you can eat",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(verticalArrangement = Arrangement.Top) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = fruits,
                            onCheckedChange = {
                                foodIntakeViewModel.updateFruits(it)

                            })
                        Text(
                            text = "Fruits",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = redMeat,
                            onCheckedChange = { foodIntakeViewModel.updateRedMeat(it) })
                        Text(
                            text = "Red Meat",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = fish,
                            onCheckedChange = { foodIntakeViewModel.updateFish(it) })
                        Text(
                            text = "Fish",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.Top) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = vegetables,
                            onCheckedChange = { foodIntakeViewModel.updateVegetables(it) })
                        Text(
                            text = "Vegetables",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = seafood,
                            onCheckedChange = { foodIntakeViewModel.updateSeafood(it) })
                        Text(
                            text = "Seafood",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = eggs,
                            onCheckedChange = { foodIntakeViewModel.updateEggs(it) })
                        Text(
                            text = "Eggs",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.Top) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = grains,
                            onCheckedChange = { foodIntakeViewModel.updateGrains(it) })
                        Text(
                            text = "Grains",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = poultry,
                            onCheckedChange = { foodIntakeViewModel.updatePoultry(it) })
                        Text(
                            text = "Poultry",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier.width(28.dp),
                            checked = nutsSeeds,
                            onCheckedChange = { foodIntakeViewModel.updateNutsSeeds(it) })
                        Text(
                            text = "Nuts/Seeds",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))

            Text(
                text = "Your Persona",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = "People can be broadly classified into 6 different types based on their eating preferences. Click on each button below to find out the different types, and select the type that best fits you!",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Button(
                        contentPadding = PaddingValues(8.dp),
                        onClick = {
                            foodIntakeViewModel.updateModalPersona("Health Devotee")
                            foodIntakeViewModel.updateModalPersonaImage(R.drawable.persona_1)
                            foodIntakeViewModel.updateModalPersonaDescription(
                                "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy."
                            )
                            foodIntakeViewModel.showDialog()
                        },
                        shape = shapes.small
                    ) {
                        Text(
                            text = "Health Devotee",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Button(
                        contentPadding = PaddingValues(8.dp),
                        onClick = {

                            foodIntakeViewModel.updateModalPersona("Mindful Eater")
                            foodIntakeViewModel.updateModalPersonaImage(R.drawable.persona_2)
                            foodIntakeViewModel.updateModalPersonaDescription(
                                "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media."
                            )
                            foodIntakeViewModel.showDialog()
                        },
                        shape = shapes.small
                    ) {
                        Text(
                            text = "Mindful Eater",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Button(
                        contentPadding = PaddingValues(8.dp),
                        onClick = {
                            foodIntakeViewModel.updateModalPersona("Wellness Striver")
                            foodIntakeViewModel.updateModalPersonaImage(R.drawable.persona_3)
                            foodIntakeViewModel.updateModalPersonaDescription(
                                "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go."
                            )
                            foodIntakeViewModel.showDialog()
                        },
                        shape = shapes.small
                    ) {
                        Text(
                            text = "Wellness Striver",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        contentPadding = PaddingValues(8.dp),
                        onClick = {
                            foodIntakeViewModel.updateModalPersona("Balance Seeker")
                            foodIntakeViewModel.updateModalPersonaImage(R.drawable.persona_4)
                            foodIntakeViewModel.updateModalPersonaDescription(
                                "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips."
                            )
                            foodIntakeViewModel.showDialog()
                        },
                        shape = shapes.small
                    ) {
                        Text(
                            text = "Balance Seeker",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Button(
                        contentPadding = PaddingValues(8.dp),
                        onClick = {
                            foodIntakeViewModel.updateModalPersona("Health Procrastinator")
                            foodIntakeViewModel.updateModalPersonaImage(R.drawable.persona_5)
                            foodIntakeViewModel.updateModalPersonaDescription(
                                "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life."
                            )
                            foodIntakeViewModel.showDialog()
                        },
                        shape = shapes.small
                    ) {
                        Text(
                            text = "Health Procrasinator",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Button(
                        contentPadding = PaddingValues(8.dp),
                        onClick = {
                            foodIntakeViewModel.updateModalPersona("Food Carefree")
                            foodIntakeViewModel.updateModalPersonaImage(R.drawable.persona_6)
                            foodIntakeViewModel.updateModalPersonaDescription(
                                "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat."
                            )
                            foodIntakeViewModel.showDialog()
                        },
                        shape = shapes.small
                    ) {
                        Text(
                            text = "Food Carefree",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }


            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            Text(
                text = "Which persona best fits you?",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            var expanded by remember { mutableStateOf(false) }
            val personas = listOf(
                "Health Devotee",
                "Mindful Eater",
                "Wellness Striver",
                "Balance Seeker",
                "Health Procrastinator",
                "Food Carefree"
            )

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = persona,
                    shape = shapes.extraLarge,
                    placeholder = { Text("Select option") },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded,
                        )
                    },
                    onValueChange = { },

                    )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.heightIn(max = 150.dp).fillMaxWidth(),
                ) {
                    personas.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                // Update the selected item and close the menu
                                foodIntakeViewModel.updatePersona(item)
                                expanded = false
                            }
                        )
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            Text(
                text = "Timing",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "What time of day approx. do you normally eat your biggest meal?",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 24.dp),
                        textAlign = TextAlign.Justify,
                        softWrap = true
                    )

                    val mTimePickerDialog1 = timePickerFun(1, time1, foodIntakeViewModel)

                    OutlinedButton(
                        border = BorderStroke(width = 2.dp, color = Color.LightGray),
                        modifier = Modifier.width(115.dp),
                        contentPadding = PaddingValues(start = 10.dp),
                        onClick = {
                            mTimePickerDialog1.show()
                        },
                        shape = shapes.small
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,

                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.time_icon),
                                contentDescription = "Time icon",
                                modifier = Modifier.size(18.dp),

                                )
                            Text(
                                text = time1,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "What time of day approx. do you go to sleep at night?",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 24.dp),
                        textAlign = TextAlign.Justify,
                        softWrap = true
                    )

                    val mTimePickerDialog2 = timePickerFun(2, time2, foodIntakeViewModel)

                    OutlinedButton(
                        border = BorderStroke(width = 2.dp, color = Color.LightGray),
                        modifier = Modifier.width(115.dp),
                        contentPadding = PaddingValues(start = 10.dp),
                        onClick = {
                            mTimePickerDialog2.show()
                        },
                        shape = shapes.small
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,

                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.time_icon),
                                contentDescription = "Time icon",
                                modifier = Modifier.size(18.dp),

                                )
                            Text(
                                text = time2,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "What time of day approx. do you wake up in the morning?",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 24.dp),
                        textAlign = TextAlign.Justify,
                        softWrap = true
                    )

                    val mTimePickerDialog3 = timePickerFun(3, time3, foodIntakeViewModel)

                    OutlinedButton(
                        border = BorderStroke(width = 2.dp, color = Color.LightGray),
                        modifier = Modifier.width(115.dp),
                        contentPadding = PaddingValues(start = 10.dp),
                        onClick = {
                            mTimePickerDialog3.show()
                        },
                        shape = shapes.small
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,

                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.time_icon),
                                contentDescription = "Time icon",
                                modifier = Modifier.size(18.dp),

                                )
                            Text(
                                text = time3,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp)
                        .width(150.dp),


                    onClick = {
                        foodIntakeViewModel.updateFoodIntake(AuthManager.getUserID().toString())
                    },
                    shape = shapes.medium

                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.save_icon),
                        contentDescription = "Save icon"
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "Save",
                    )

                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { foodIntakeViewModel.hideDialog() },
                icon = {
                    Image(
                        painter = painterResource(id = modalPersonaImage),
                        contentDescription = "Persona image",
                        modifier = Modifier.size(150.dp)
                    )
                },
                title = {
                    Text(
                        text = modalPersona,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                text = {
                    Text(
                        text = modalPersonaDescription,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center

                    )
                },
                confirmButton = {
                },
                dismissButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { foodIntakeViewModel.hideDialog() },
                            shape = shapes.medium

                        ) {
                            Text(
                                text = "Dismiss",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                ),
                            )
                        }
                    }
                }
            )
        }
    }

}

@Composable
fun timePickerFun(index: Int, time: String, viewModel: FoodIntakeViewModel): TimePickerDialog {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()

    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    mCalendar.time = Calendar.getInstance().time

    return TimePickerDialog(
        mContext,
        { _, hour: Int, minute: Int ->
            viewModel.updateTime(index, String.format("%02d:%02d", hour, minute))
        },
        mHour,
        mMinute,
        false
    )

}

