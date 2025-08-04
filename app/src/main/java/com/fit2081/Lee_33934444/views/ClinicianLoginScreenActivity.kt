package com.fit2081.Lee_33934444.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fit2081.Lee_33934444.viewmodels.ClinicianViewModel
import com.fit2081.Lee_33934444.views.ui.theme.NutriTrackTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianLoginScreen(
    modifier: Modifier = Modifier,
    clinicianViewModel: ClinicianViewModel = viewModel(),
    selectedItem: MutableState<Int>,
    navController: NavHostController,) {

    val inputKey = clinicianViewModel.inputKey

    val _context = LocalContext.current
    LaunchedEffect(Unit) {

        clinicianViewModel.clinicianEvent.collect { event ->
            when (event) {
                true -> {
                    Toast.makeText(_context, " Clinician login successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("clinician_dashboard_screen")

                }

                false -> {
                    Toast.makeText(_context, "Incorrect clinician key", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Clinician Login",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("Settings")
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        bottomBar = {
            MyBottomAppBar(navController, selectedItem)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp),
                value = inputKey,
                shape = shapes.medium,
                singleLine = true,

                onValueChange = {
                    clinicianViewModel.updateInputKey(it)
                },
                label = {
                    Text(
                        text = "Clinician Key",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text(text = "Enter your clinician key") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 12.dp, start = 24.dp, end = 24.dp),
                shape = shapes.medium,
                onClick = {
                    clinicianViewModel.validateKey(inputKey)
                },
            ) {
                Text("Clinician Login")
            }

        }

    }
}