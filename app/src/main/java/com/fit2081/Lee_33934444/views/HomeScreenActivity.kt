package com.fit2081.Lee_33934444.views

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fit2081.Lee_33934444.R
import com.fit2081.Lee_33934444.data.AuthManager
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel
import com.fit2081.Lee_33934444.ui.theme.*
import java.io.BufferedReader
import java.io.InputStreamReader

//class HomeScreenActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            val patientsViewModel = ViewModelProvider(
//                this,
//                PatientsViewModel.PatientsViewModelFactory(applicationContext)
//            ).get(PatientsViewModel::class.java)
//
//            NutriTrackTheme {
//                val navController: NavHostController = rememberNavController()
//                var selectedItem: MutableState<Int> = remember { mutableIntStateOf(0) }
//
//            }
//        }
//    }
//}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    patientsViewModel: PatientsViewModel,
    selectedItem: MutableState<Int>
) {
//

    LaunchedEffect(Unit) {
        patientsViewModel.loadLoginPatient()
    }

    Log.d("auth ", "Login Patient: ${AuthManager.getUserID().toString()}")
    val loginPatient = patientsViewModel.loginPatientFlow.collectAsStateWithLifecycle().value
    Log.d("HomeScreen", "Login Patient: $loginPatient")


    val mContext = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MyBottomAppBar(navController, selectedItem)
        }
    ) { innerPadding ->


        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Top,

            ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Hello,",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )
            Text(
                text = "${loginPatient.fName} ${loginPatient.lName}",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(
                    text = "You've already filled in your Food intake Questionnaire, but you can change details here:",
                    style = TextStyle(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                )

                Button(
                    shape = shapes.small,
                    onClick = {
                        navController.navigate("food_intake_screen")
                    },
                    contentPadding = PaddingValues(start = 14.dp, end = 14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 4.dp)
                        )
                        Text(text = "Edit")
                    }
                }

            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plate),
                    contentDescription = "Food",
                    modifier = Modifier.size(300.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Score",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TextButton(
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        selectedItem.value = 1
                        navController.navigate("Insights")

                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "See all scores",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Image(
                            painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                            contentDescription = "Right Arrow",
                            modifier = Modifier.size(20.dp)
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
                    text = "Your Food Quality Score"
                )

                Text(
                    text = "${loginPatient.totalScore}/100",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )


            }
            Spacer(modifier = Modifier.padding(8.dp))
            HorizontalDivider(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            Text(
                text = "What is the Food Quality Score?",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.\n" +
                        "\n" + "This personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
                style = TextStyle(
                    fontSize = 14.sp
                )
            )

        }
    }

}

@Composable
fun MyBottomAppBar(navController: NavHostController, selectedItem: MutableState<Int>) {

    val items = listOf(
        "Home",
        "Insights",
        "NutriCoach",
        "Settings"
    )
    NavigationBar(
        contentColor = LightGreen
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    when (item) {
                        "Home" -> Icon(
                            painter = painterResource(id = R.drawable.baseline_home_24),
                            contentDescription = "Home"
                        )

                        "Insights" -> Icon(
                            painter = painterResource(id = R.drawable.baseline_insights_24),
                            contentDescription = "Insights"
                        )

                        "NutriCoach" -> Icon(
                            painter = painterResource(id = R.drawable.baseline_hearing_24),
                            contentDescription = "NutriCoach"
                        )

                        "Settings" -> Icon(
                            painter = painterResource(id = R.drawable.baseline_settings_24),
                            contentDescription = "Settings"
                        )
                    }
                },
                label = { Text(text = item) },
                selected = selectedItem.value == index,
                onClick = {
                    selectedItem.value = index
                    navController.navigate(item)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = DarkGreen,
                    selectedTextColor = DarkGreen,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.DarkGray,
                    selectedIndicatorColor = ExtraLightGreen,
                    disabledIconColor = Color.Gray,
                    disabledTextColor = Color.Gray,
                )

            )
        }
    }
}
