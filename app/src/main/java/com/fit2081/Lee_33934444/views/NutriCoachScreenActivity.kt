package com.fit2081.Lee_33934444.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.geometry.Size
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fit2081.Lee_33934444.R
import com.fit2081.Lee_33934444.data.AuthManager
import com.fit2081.Lee_33934444.data.foodintake.FoodIntakeViewModel
import com.fit2081.Lee_33934444.data.network.FruitsViewModel
import com.fit2081.Lee_33934444.data.nutricoachtips.NutriCoachViewModel
import com.fit2081.Lee_33934444.views.UiState
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel
import com.fit2081.Lee_33934444.ui.theme.DarkGreen
import com.fit2081.Lee_33934444.ui.theme.ExtraLightGreen
import com.fit2081.Lee_33934444.ui.theme.LightGreen
import com.fit2081.Lee_33934444.ui.theme.MediumGreen
import kotlinx.coroutines.coroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriCoachScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    patientsViewModel: PatientsViewModel,
    foodIntakeViewModel: FoodIntakeViewModel,
    nutriCoachViewModel: NutriCoachViewModel,
    fruitsViewModel: FruitsViewModel,
    selectedItem: MutableState<Int>
) {

    val isLoading = remember { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current

    val fruitInfoState = fruitsViewModel.fruitInfo.collectAsState()
    val fruitInfo = fruitInfoState.value

    val fruit = nutriCoachViewModel.inputFruit
    val _context = LocalContext.current
    var result by rememberSaveable { mutableStateOf("") }
    val size = remember { mutableStateOf(Size.Zero) }
    val uiState by nutriCoachViewModel.uiState.collectAsState()

    val allTips by nutriCoachViewModel.allTips.collectAsState()
    nutriCoachViewModel.getAllTips(AuthManager.getUserID().toString())

    val loginPatient = patientsViewModel.loginPatientFlow.collectAsStateWithLifecycle().value

    val showDialog by nutriCoachViewModel.showDialog.collectAsState()

    foodIntakeViewModel.loadFoodIntake(AuthManager.getUserID().toString())
    val foodIntake by foodIntakeViewModel.foodIntakeStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        patientsViewModel.loadLoginPatient()
        fruitsViewModel.fetchEvent.collect { event ->
            if (!event) {
                Toast.makeText(_context, "Failed to fetch fruit details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "NutriCoach",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.width(150.dp),
                containerColor = DarkGreen,

                onClick = {
                    nutriCoachViewModel.getAllTips(AuthManager.getUserID().toString())
                    nutriCoachViewModel.showDialog()
                    Log.d("NutriCoachScreen", "All tips: ${allTips}")
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.stack),
                        contentDescription = "stack",
                        modifier = Modifier.size(30.dp).padding(end = 8.dp),
                    )
                    Text(
                        text = "Show All Tips",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }


            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        )
        {
            if (!patientsViewModel.checkPatientFruitScore(loginPatient)) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    Text(
                        text = "Fruit Name",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(start = 8.dp)

                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).width(250.dp),
                            value = fruit,
                            shape = shapes.medium,
                            singleLine = true,
                            onValueChange = { nutriCoachViewModel.updateInputFruit(it) },

                            placeholder = { Text(text = "Enter fruit name") },
                        )
                        Spacer(Modifier.weight(1f))
                        Button(
                            shape = shapes.medium,
                            modifier = Modifier
                                .padding(start = 8.dp).height(60.dp).fillMaxWidth(),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                fruitsViewModel.getFruitInfo(fruit)
                                focusManager.clearFocus()
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "Search",
                                modifier = Modifier.size(30.dp).padding(end = 4.dp),

                                )
                            Text(
                                text = "Details",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row(
                        modifier = Modifier
                            .background(color = Color.LightGray, shape = shapes.medium)
                            .padding(8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "family",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp).width(200.dp),
                            text = ": ${fruitInfo.family ?: "N/A"}",
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
                            .padding(8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "calories",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp).width(200.dp),
                            text = ": ${fruitInfo.nutritions?.calories ?: "N/A"}",
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
                            .padding(8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "fat",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp).width(200.dp),
                            text = ": ${fruitInfo.nutritions?.fat ?: "N/A"}",
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
                            .padding(8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "sugar",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp).width(200.dp),
                            text = ": ${fruitInfo.nutritions?.sugar ?: "N/A"}",
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
                            .padding(8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "carbohydrates",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp).width(200.dp),
                            text = ": ${fruitInfo.nutritions?.carbohydrates ?: "N/A"}",
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
                            .padding(8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "protein",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp).width(200.dp),
                            text = ": ${fruitInfo.nutritions?.protein ?: "N/A"}",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
            else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .padding(8.dp).onGloballyPositioned { coordinates ->
                            size.value = coordinates.size.toSize()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://picsum.photos/200/300")
                                .crossfade(true)
                                .listener(
                                    onStart = { isLoading.value = true },
                                    onSuccess = { _, _ -> isLoading.value = false },
                                    onError = { _, _ -> isLoading.value = false }
                                )
                                .build(),
                            contentDescription = "NutriCoach Image",
                            modifier = Modifier.fillMaxSize()
                        )

                        if (isLoading.value) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }





                }
            }

                HorizontalDivider(modifier = Modifier.padding(top = 24.dp, bottom = 18.dp))

                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    shape = shapes.small,
                    onClick = {
                        nutriCoachViewModel.sendPrompt(result, foodIntake)
                    },
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = "Motivational Icon",
                        modifier = Modifier.size(30.dp).padding(end = 8.dp),
                    )

                    Text(
                        text = "Motivational Message (AI)",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    var textColor = MaterialTheme.colorScheme.onSurface
                    if (uiState is UiState.Error) {
                        textColor = MaterialTheme.colorScheme.error
                        result = (uiState as UiState.Error).errorMessage
                    } else if (uiState is UiState.Success) {
                        textColor = MaterialTheme.colorScheme.onSurface
                        result = (uiState as UiState.Success).outputText

                        LaunchedEffect(Unit) {
                            nutriCoachViewModel.insertTips(
                                result,
                                AuthManager.getUserID().toString()
                            )
                        }
                    }

                    val scrollState = rememberScrollState()
                    Text(
                        text = result,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)

                    )
                }
        }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { nutriCoachViewModel.hideDialog() },
                    confirmButton = {
                        Button(
                            shape = shapes.medium,
                            onClick = { nutriCoachViewModel.hideDialog() }
                        ) {
                            Text("Done")
                        }
                    },
                    title = {
                        Text(
                            text = "AI tips",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    text = {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 400.dp)
                        ) {
                            items(allTips) { tip ->
                                Log.d("NutriCoachScreen", "Tip: $tip")
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    colors =
                                        CardDefaults.cardColors(
                                            containerColor = ExtraLightGreen,
                                        )


                                ) {
                                    Text(
                                        text = tip,
                                        modifier = Modifier.padding(12.dp),
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }
                    }
                )
            }

        }


    }


