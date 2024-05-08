package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fi.tuni.weather_forecasting_app.ui.theme.IndigoGradientBackground
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel

@Composable
fun CurrentDayForecast(date: String, opacity: Float, forecastViewModel: WeatherDataViewModel) {

    // model for forecast
    val isRefreshing = remember { forecastViewModel.isRefreshing }

    var hourlyData = remember(forecastViewModel.forecastData) {
        forecastViewModel.getHourlyData(date)
    }

    // Call getHourlyData when the data inside viewmodel changes
    LaunchedEffect(forecastViewModel.forecastData) {
        hourlyData = forecastViewModel.getHourlyData(date)
    }

    if (isRefreshing.value) {
        // show loading
        Text(text = "Loading...", modifier = Modifier.fillMaxWidth().alpha(opacity), textAlign = TextAlign.Center)
    } else {
        Box(modifier = Modifier.alpha(opacity)) {
            LazyColumn {
                items(hourlyData.size + 1) {
                    if (it < hourlyData.size) {
                        Box(
                            modifier = Modifier.padding(vertical = 30.dp, horizontal = 15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    //modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    text = hourlyData[it].hour,
                                )
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        text = hourlyData[it].weatherConditions
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        text = "${hourlyData[it].temperature} °C"
                                    )
                                }

                                Image(
                                    painter = painterResource(id = hourlyData[it].backgroundImage),
                                    contentDescription = "background image",
                                    modifier = Modifier
                                        .size(LocalConfiguration.current.screenWidthDp.dp / 5)
                                        .clip(RectangleShape)
                                        .border(
                                            width = 2.dp,
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSurface
                                            ),
                                            shape = RectangleShape
                                        ),
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.TopCenter,
                                )
                            }
                        }

                        Divider(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            color = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onSurface)
                        )
                    } else {
                        Box(modifier = Modifier.padding(50.dp)) {}
                    }
                }
            }

            // Clickable button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                Button(
                    onClick = {
                        forecastViewModel.refreshWeatherData()
                    },
                    modifier = Modifier.align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(Color.Transparent.compositeOver(
                        MaterialTheme.colorScheme.secondary))
                ) {
                    Text(
                        text = "Refresh",
                        color = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }
        }
    }
}
