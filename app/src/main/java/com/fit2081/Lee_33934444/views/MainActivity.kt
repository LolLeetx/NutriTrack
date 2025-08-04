package com.fit2081.Lee_33934444.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fit2081.Lee_33934444.R
import com.fit2081.Lee_33934444.data.NutriTrackDatabase
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeViewModel
import com.fit2081.Lee_33934444.data.network.FruitsViewModel
import com.fit2081.Lee_33934444.data.nutricoachtips.NutriCoachViewModel
import com.fit2081.Lee_33934444.data.patient.Patient
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel
import com.fit2081.Lee_33934444.ui.theme.NutriTrackTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val patientsViewModel = ViewModelProvider(
            this,
            PatientsViewModel.PatientsViewModelFactory(applicationContext)
        ).get(PatientsViewModel::class.java)

        val foodIntakeViewModel = ViewModelProvider(
            this,
            FoodIntakeViewModel.FoodIntakeViewModelFactory(applicationContext)
        ).get(FoodIntakeViewModel::class.java)

        val nutriCoachViewModel = ViewModelProvider(
            this,
            NutriCoachViewModel.NutriCoachViewModelFactory(applicationContext)
        ).get(NutriCoachViewModel::class.java)

        val fruitsviewModel:FruitsViewModel = ViewModelProvider(this)[FruitsViewModel::class.java]

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriTrackTheme {

                val navController: NavHostController = rememberNavController()
                var selectedItem: MutableState<Int> = remember { mutableIntStateOf(0) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        patientsViewModel = patientsViewModel,
                        foodIntakeViewModel = foodIntakeViewModel,
                        nutriCoachViewModel = nutriCoachViewModel,
                        fruitsViewModel = fruitsviewModel,
                        selectedItem = selectedItem
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen (modifier: Modifier = Modifier, patientsViewModel: PatientsViewModel,navController: NavHostController) {
    val allPatientsID by patientsViewModel.allPatientsID.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        patientsViewModel.loadCSV()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NutriTrack",
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "NutriTrack Logo",
            modifier = Modifier.width(100.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            modifier = Modifier.width(270.dp),
            text = "This app provides general health and nutrition information for educational purposes only. It is not intended as medical advice,diagnosis, or treatment. Always consult a qualified healthcare professional before making any changes to your diet, exercise, or health regimen. \n Use this app at your own risk.\n If you’d like to an Accredited Practicing Dietitian (APD), please visit the Monash Nutrition/Dietetics Clinic (discounted rates for students): \n https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,

                ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(84.dp))
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            shape = MaterialTheme.shapes.medium,
            onClick = {

                navController.navigate("login_screen")
            },
        ) {

            Text("Login")
        }
        Spacer(modifier = Modifier.padding(32.dp))

        Text(
            text = "Lee Teck Xian (33934444)",
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
            )
        )


    }
}

@Composable
fun NavHost(
    navController: NavHostController,
    patientsViewModel: PatientsViewModel,
    foodIntakeViewModel: FoodIntakeViewModel,
    nutriCoachViewModel: NutriCoachViewModel,
    fruitsViewModel: FruitsViewModel,
    selectedItem: MutableState<Int>,
    modifier: Modifier = Modifier,// Optional modifier for customizing layout
) {
    // Set up navigation host to manage different login screens
    NavHost(navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(
                navController = navController,
                patientsViewModel = patientsViewModel
            )
        }
        composable("login_screen") {
            LoginScreen(
                navController = navController,
                patientsViewModel = patientsViewModel
            )
        }
        composable("register_screen") {
            RegisterScreen(
                navController = navController,
                patientsViewModel = patientsViewModel,
                foodIntakeViewModel = foodIntakeViewModel
            )
        }

        composable("food_intake_screen") {
            FoodIntakeScreen(
                navController = navController,
                patientsViewModel = patientsViewModel,
                foodIntakeViewModel = foodIntakeViewModel
            )
        }

        composable("Home") {
            HomeScreen(
                modifier = modifier,
                navController = navController,
                patientsViewModel = patientsViewModel,
                selectedItem = selectedItem
            )
        }
        composable("Insights") {
            InsightScreen(
                modifier = modifier,
                navController = navController,
                patientsViewModel = patientsViewModel,
                selectedItem = selectedItem
            )
        }

        composable("NutriCoach") {
            NutriCoachScreen(
                navController = navController,
                patientsViewModel = patientsViewModel,
                foodIntakeViewModel = foodIntakeViewModel,
                nutriCoachViewModel = nutriCoachViewModel,
                fruitsViewModel = fruitsViewModel,
                selectedItem = selectedItem
            )
        }

        composable("Settings") {
            SettingScreen(
                modifier = modifier,
                navController = navController,
                patientsViewModel = patientsViewModel,
                foodIntakeViewModel = foodIntakeViewModel,
                selectedItem = selectedItem
            )

        }

        composable("clinician_login_screen") {
            ClinicianLoginScreen(
                modifier = modifier,
                navController = navController,
                selectedItem = selectedItem
            )

        }

        composable("clinician_dashboard_screen") {
            ClinicianDashboardScreen(
                modifier = modifier,
                patientsViewModel = patientsViewModel,
                navController = navController,
                selectedItem = selectedItem
            )
        }

    }
}



