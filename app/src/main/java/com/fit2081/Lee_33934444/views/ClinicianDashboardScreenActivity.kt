package com.fit2081.Lee_33934444.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fit2081.Lee_33934444.R
import com.fit2081.Lee_33934444.data.AuthManager
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeViewModel
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel
import com.fit2081.Lee_33934444.dataclass.DataPattern
import com.fit2081.Lee_33934444.ui.theme.ExtraLightGreen
import com.fit2081.Lee_33934444.ui.theme.MediumGreen
import com.fit2081.Lee_33934444.viewmodels.ClinicianDashboardViewModel
import com.fit2081.Lee_33934444.viewmodels.ClinicianViewModel
import com.fit2081.Lee_33934444.views.ui.theme.NutriTrackTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianDashboardScreen(
    modifier: Modifier = Modifier,
    patientsViewModel: PatientsViewModel,
    selectedItem: MutableState<Int>,
    clinicianDashboardViewModel: ClinicianDashboardViewModel = viewModel(),
    navController: NavHostController
    ) {

    val _context = LocalContext.current
    val averageScore by patientsViewModel.averageScore.collectAsStateWithLifecycle()
    val rawData by patientsViewModel.rawData.collectAsStateWithLifecycle()

    var result by remember { mutableStateOf(DataPattern()) }
    val uiState by clinicianDashboardViewModel.uiState.collectAsState()



    LaunchedEffect(Unit) {
        patientsViewModel.loadRawData()
        patientsViewModel.loadAverageScore()
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Clinician Dashboard",
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
                .padding(innerPadding).padding(start = 16.dp, end = 16.dp),
        ) {
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier
                    .background(color = Color.LightGray, shape = shapes.medium)
                    .padding(8.dp).
                    fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Average HEIFA (MALE)",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp).width(130.dp),
                    text = " : ${averageScore["Male"] ?: "N/A"}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier
                    .background(color = Color.LightGray, shape = shapes.medium)
                    .padding(8.dp).
                    fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Average HEIFA (FEMALE)",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp).width(130.dp),
                    text = " : ${averageScore["Female"] ?: "N/A"}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 24.dp, end = 24.dp),
                shape = shapes.medium,
                onClick = {
                    patientsViewModel.loadRawData()
                    Log.d("ClinicianDashboardScreen", "Raw Data: $rawData")
                    clinicianDashboardViewModel.getPattern(rawData.toString())
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search",
                    modifier = Modifier.size(24.dp).padding(end = 8.dp),
                    )
                Text("Find data pattern")
            }

            if (uiState is UiState_DataPattern.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                var textColor = MaterialTheme.colorScheme.onSurface
                if (uiState is UiState_DataPattern.Error) {
                    textColor = MaterialTheme.colorScheme.error
                    Toast.makeText(_context, "Error fetching result", Toast.LENGTH_SHORT).show()
                } else if (uiState is UiState_DataPattern.Success) {
                    textColor = MaterialTheme.colorScheme.onSurface
                    result = (uiState as UiState_DataPattern.Success).outputText
                }

                val scrollState = rememberScrollState()
                Log.d("ClinicianDashboardScreen", "Result: $result")

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),

                ){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ExtraLightGreen
                        )
                    ){
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ){
                            Text(
                                text = result.pattern1Key?: "N/A",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = result.pattern1info?: "N/A",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )
                        }

                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ExtraLightGreen
                        )
                    ){
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ){
                            Text(
                                text = result.pattern2Key?: "N/A",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = result.pattern2info?: "N/A",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )
                        }

                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ExtraLightGreen
                        )
                    ){
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ){
                            Text(
                                text = result.pattern3Key?: "N/A",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = result.pattern3info?: "N/A",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                ),
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )
                        }

                    }

                }



            }

        }


    }
}