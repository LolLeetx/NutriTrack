package com.fit2081.Lee_33934444.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.fit2081.Lee_33934444.R
import com.fit2081.Lee_33934444.data.AuthManager
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeViewModel
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel

import kotlin.math.log

//class SettingScreenActivity : ComponentActivity() {
//    @OptIn(ExperimentalMaterial3Api::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            NutriTrackTheme {
//                Scaffold(
//                    topBar = {
//                        CenterAlignedTopAppBar(
//                            title = {
//                                Text(
//                                    text = "Settings",
//                                    style = TextStyle(
//                                        fontSize = 24.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                )
//                            }
//                        )
//                    }
//                ) { innerPadding ->
//                    SettingScreen(Modifier.padding(innerPadding))
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    patientsViewModel: PatientsViewModel,
    foodIntakeViewModel: FoodIntakeViewModel,
    selectedItem: MutableState<Int>,
    navController: NavHostController
) {
    val context = LocalContext.current

    patientsViewModel.loadLoginPatient()
    val loginPatient = patientsViewModel.loginPatientFlow.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        },
        bottomBar = {
            MyBottomAppBar(navController, selectedItem)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "ACCOUNT",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.user_pic),
                    contentDescription = "User Icon",
                    modifier = Modifier.padding(end = 16.dp).size(40.dp)
                )
                Text(
                    text = "${loginPatient.fName} ${loginPatient.lName}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )

            }
            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_call),
                    contentDescription = "call Icon",
                    modifier = Modifier.padding(end = 16.dp).size(40.dp)
                )
                Text(
                    text = "${loginPatient.phoneNumber}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )

            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.id_icon),
                    contentDescription = "Id Icon",
                    modifier = Modifier.padding(end = 16.dp).size(40.dp)
                )
                Text(
                    text = "${loginPatient.userID}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )

            }
            HorizontalDivider(modifier = Modifier.padding(top = 24.dp, bottom = 24.dp))

            Text(
                text = "OTHER SETTINGS",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextButton(
                onClick = {
                    patientsViewModel.removeLoginPatient()
                    foodIntakeViewModel.removeFoodIntake()
                    selectedItem.value = 0
                    AuthManager.logout()
                    navController.navigate("login_screen")
                    Toast.makeText(context, "You have logged out", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "logout Icon",
                        modifier = Modifier.padding(end = 16.dp).size(40.dp)
                    )
                    Text(
                        text = "Log out",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.right_arrow),
                        contentDescription = "Arrow Icon",
                        modifier = Modifier.padding(start = 16.dp).size(40.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(6.dp))

            TextButton(
                onClick = {
                    navController.navigate("clinician_login_screen")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.login),
                        contentDescription = "logout Icon",
                        modifier = Modifier.padding(end = 16.dp).size(40.dp)
                    )
                    Text(
                        text = "Clinician Login",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.right_arrow),
                        contentDescription = "Arrow Icon",
                        modifier = Modifier.padding(start = 16.dp).size(40.dp)
                    )
                }

            }


        }

    }
}