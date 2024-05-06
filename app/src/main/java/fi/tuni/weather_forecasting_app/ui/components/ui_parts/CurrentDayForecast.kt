package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel

@Composable
fun CurrentDayForecast(date: String, forecastViewModel: WeatherDataViewModel) {

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
        Text(text = "Loading...", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    } else {
        Box {
            LazyColumn {
                items(hourlyData.size + 1) {
                    if (it < hourlyData.size) {
                        Box(modifier = Modifier.padding(30.dp)) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                text = hourlyData[it].hour,
                            )
                            Column(verticalArrangement = Arrangement.Top) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = hourlyData[it].weatherConditions
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = "${hourlyData[it].temperature} °C"
                                )
                            }
                        }

                        Divider(modifier = Modifier.padding(horizontal = 20.dp))
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
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = "Refresh", color = Color.White)
                }
            }
        }
    }
}
