package com.fit2081.Lee_33934444.views

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.fit2081.Lee_33934444.R
import com.fit2081.Lee_33934444.data.patient.PatientsViewModel
import com.fit2081.Lee_33934444.ui.theme.ExtraLightGreen
import com.fit2081.Lee_33934444.ui.theme.NutriTrackTheme
import java.io.BufferedReader
import java.io.InputStreamReader

class InsightsScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriTrackTheme {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightScreen(
    modifier: Modifier = Modifier,
    patientsViewModel: PatientsViewModel,
    selectedItem: MutableState<Int>,
    navController: NavHostController
) {

    patientsViewModel.loadLoginPatient()
    val loginPatient = patientsViewModel.loginPatientFlow.collectAsStateWithLifecycle().value
    Log.d("HomeScreen", "Login Patient: $loginPatient")
    val mContext = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Insights: Food Score",
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Vegetables",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )

                LinearProgressIndicator(
                    progress = { loginPatient.vegetablesScore?.toFloat()?.div(10f) ?: 0f },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round, trackColor = ExtraLightGreen

                )
                Text(
                    text = "${loginPatient.vegetablesScore}/10",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fruits",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.fruitsScore?.toFloat()?.div(10f) ?: 0f },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.fruitsScore}/10",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )

                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Grains & Cereals",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.grainCerealScore?.toFloat()?.div(5f) ?: 0f },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.grainCerealScore}/5",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Whole Grains",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.wholeGrainScore?.toFloat()?.div(5f) ?: 0f  },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.wholeGrainScore}/5",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Meat & Alternatives",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.meatScore?.toFloat()?.div(10f) ?: 0f  },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.meatScore}/10",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Dairy",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.dairyScore?.toFloat()?.div(10f) ?: 0f },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.dairyScore}/10",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Water",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.waterScore?.toFloat()?.div(5f) ?: 0f },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.waterScore}/5",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Saturated Fats",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.satFatsScore?.toFloat()?.div(5f) ?: 0f  },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.satFatsScore}/5",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Unsaturated Fats",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.unsatFatsScore?.toFloat()?.div(5f) ?: 0f  },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.unsatFatsScore}/5",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sodium",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.sodiumScore?.toFloat()?.div(10f) ?: 0f  },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.sodiumScore}/10",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sugar",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.sugarScore?.toFloat()?.div(10f) ?: 0f },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.sugarScore}/10",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Alcohol",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )
                LinearProgressIndicator(
                    progress = { loginPatient.alcoholScore?.toFloat()?.div(5f) ?: 0f },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.alcoholScore}/5",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    text = "Discretionary Foods",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(140.dp)
                )

                LinearProgressIndicator(
                    progress = { loginPatient.discretionaryScore?.toFloat()?.div(10f) ?: 0f  },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                        .height(12.dp),
                    strokeCap = StrokeCap.Round,
                    trackColor = ExtraLightGreen
                )
                Text(
                    text = "${loginPatient.discretionaryScore}/10",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(50.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )


            }

            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                ) {
                    Text(

                        text = "Total Food Quality Score",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LinearProgressIndicator(
                            progress = { loginPatient.totalScore?.toFloat()?.div(100f) ?: 0f  },
                            modifier = Modifier
                                .weight(1f)
                                .height(12.dp),
                            strokeCap = StrokeCap.Round,
                            trackColor = ExtraLightGreen
                        )
                        Text(
                            text = "${loginPatient.totalScore}/100",
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                Button(
                    onClick = {
                        val shareIntent = Intent(ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Yo guys, my HEIFA score is ${loginPatient.totalScore}/100!"
                        )
                        mContext.startActivity(
                            Intent.createChooser(
                                shareIntent,
                                "Share your HEIFA score via"
                            )
                        )
                    },
                    shape = shapes.medium,
                    contentPadding = PaddingValues(8.dp)
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_share_24),
                            contentDescription = "share",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Share with someone")
                    }
                }
                Button(
                    onClick = {
                        navController.navigate("NutriCoach")
                    },
                    shape = shapes.medium,
                    contentPadding = PaddingValues(8.dp)
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_rocket_launch_24),
                            contentDescription = "Add",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Improve my diet!")
                    }
                }
            }
        }
    }
}


