package com.fit2081.Lee_33934444.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme.shapes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fit2081.Lee_33934444.data.NutriTrackDatabase
import com.fit2081.Lee_33934444.data.patient.Patient
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel
import com.fit2081.Lee_33934444.ui.theme.NutriTrackTheme
import com.fit2081.Lee_33934444.viewmodels.LoginViewModel
import com.fit2081.Lee_33934444.viewmodels.RegisterViewModel
import com.fit2081.Lee_33934444.views.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    patientsViewModel: PatientsViewModel,
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController ) {


    val password = loginViewModel.password

    var expanded by remember { mutableStateOf(false) }

    val _context = LocalContext.current

    val allPatientsID by patientsViewModel.allPatientsID.collectAsStateWithLifecycle()


    LaunchedEffect(allPatientsID) {
        if (allPatientsID.isNotEmpty()) {
            loginViewModel.updateSelectedPatientID(allPatientsID.first())
        }
    }

    val patientID = loginViewModel.selectedPatientID


    LaunchedEffect(Unit) {

        patientsViewModel.loginEvent.collect { event ->
            when (event) {
                true -> {
                    loginViewModel.logIn(loginViewModel.selectedPatientID)
                    Toast.makeText(_context, "Login successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("food_intake_screen")

                }

                false -> {
                    Toast.makeText(_context, "Incorrect credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Log In",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, top = 12.dp),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),

                    value = loginViewModel.selectedPatientID,
                    shape = shapes.medium,
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded,
                        )
                    },
                    onValueChange = {  },
                    label = {
                        Text(
                            text = "My ID (Provided by your Clinician)",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .heightIn(max = 150.dp)
                        .fillMaxWidth(),
                ) {
                    allPatientsID.forEach { patientID ->
                        DropdownMenuItem(

                            text = { Text(patientID) },
                            onClick = {
                                // update viewmodel's selectedItem
                                Log.d("LoginScreen", "Selected Patient ID: $patientID")
                                loginViewModel.updateSelectedPatientID(patientID)
                                expanded = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, top = 12.dp),
                value = password,
                shape = shapes.medium,
                singleLine = true,
                // update viewmodel's phonenumber
                onValueChange = { loginViewModel.updatePassword(it) },
                label = {
                    Text(
                        text = "Password",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text(text = "Enter your password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )


            Text(
                modifier = Modifier.padding(start = 42.dp, end = 42.dp, top = 12.dp),

                text = "This app is only for pre-registered users. Please enter your ID and password or Register to claim your account on your first visit. ",
                style = TextStyle(
                    textAlign = TextAlign.Justify,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 24.dp, end = 24.dp),
                shape = shapes.medium,
                onClick = {
                    patientsViewModel.isAuth(password, patientID)
                },
            ) {
                Text("Continue")
            }


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                shape = shapes.medium,
                onClick = {
                    navController.navigate("register_screen")
                },
            ) {
                Text("Register")
            }

        }
    }

}





