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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeViewModel
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel
import com.fit2081.Lee_33934444.ui.theme.NutriTrackTheme
import com.fit2081.Lee_33934444.viewmodels.RegisterViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen (
    navController: NavController,
    patientsViewModel: PatientsViewModel,
    foodIntakeViewModel: FoodIntakeViewModel,
    registerViewModel: RegisterViewModel = viewModel()) {
    val phoneNumber = registerViewModel.phoneNumber

    val firstName = registerViewModel.firstName
    val lastName = registerViewModel.lastName

    val password = registerViewModel.password
    val confirmPassword = registerViewModel.confirmPassword

    var expanded by remember { mutableStateOf(false) }

    val allPatientsID by patientsViewModel.allPatientsID.collectAsStateWithLifecycle()

    Log.d("PatientsID", "LoginScreen: $allPatientsID")


    LaunchedEffect(allPatientsID) {
        if (allPatientsID.isNotEmpty()) {
            registerViewModel.updateSelectedPatientID(allPatientsID.first())
        }
    }
    val patientID = registerViewModel.selectedPatientID

    val _context = LocalContext.current

    LaunchedEffect(Unit) {

        patientsViewModel.registerEvent.collect { event ->
            when (event) {
                "success" -> {
                    val patientID = registerViewModel.selectedPatientID
                    foodIntakeViewModel.init(patientID)
                    Toast.makeText(_context, "Register successful", Toast.LENGTH_SHORT).show()
                }

                "phone_num_not_match" -> {
                    Toast.makeText(_context, "Phone number not match", Toast.LENGTH_SHORT).show()
                }

                "password_not_match" -> {
                    Toast.makeText(_context, "Password not match", Toast.LENGTH_SHORT).show()
                }

                "patient_exists" -> {
                    Toast.makeText(_context, "User already registered", Toast.LENGTH_SHORT).show()
                }

                "empty_fields" -> {
                    Toast.makeText(_context, "Empty fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Register",
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

                        // viewmodel's selectedItem
                        value = registerViewModel.selectedPatientID,
                        shape = shapes.medium,
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded,
                            )
                        },
                        onValueChange = { },
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
                                    registerViewModel.updateSelectedPatientID(patientID)
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
                    value = phoneNumber,
                    shape = shapes.medium,
                    singleLine = true,

                    onValueChange = { registerViewModel.updatePhoneNumber(it) },
                    label = {
                        Text(
                            text = "Phone Number",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    placeholder = { Text(text = "Enter your phone number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 12.dp),
                    value = firstName,
                    shape = shapes.medium,
                    singleLine = true,

                    onValueChange = { registerViewModel.updateFirstName(it) },
                    label = {
                        Text(
                            text = "First Name",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    placeholder = { Text(text = "Enter your first name") }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 12.dp),
                    value = lastName,
                    shape = shapes.medium,
                    singleLine = true,

                    onValueChange = { registerViewModel.updateLastName(it) },
                    label = {
                        Text(
                            text = "Last Name",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    placeholder = { Text(text = "Enter your last name") }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 12.dp),
                    value = password,
                    shape = shapes.medium,
                    singleLine = true,

                    onValueChange = { registerViewModel.updatePassword(it) },
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

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 12.dp),
                    value = confirmPassword,
                    shape = shapes.medium,
                    singleLine = true,

                    onValueChange = { registerViewModel.updateConfirmPassword(it) },
                    label = {
                        Text(
                            text = "Confirm Password",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = { Text(text = "Enter your password again") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Text(
                    modifier = Modifier.padding(start = 42.dp, end = 42.dp, top = 12.dp),
                    text = "This app is only for pre-registered users. Please enter your ID, phone number and password to claim your account. ",
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
                        Log.d("Register", "RegisterScreen: $patientID")
                        patientsViewModel.registerPatient(
                            patientID = patientID,
                            phoneNumber = phoneNumber,
                            firstName = firstName,
                            lastName = lastName,
                            password = password,
                            confirmPassword = confirmPassword
                        )
                    },
                ) {
                    Text("Register")
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    shape = shapes.medium,
                    onClick = {
                        navController.navigate("login_screen")
                    },
                ) {
                    Text("Log in")
                }
            }

        }
    }


