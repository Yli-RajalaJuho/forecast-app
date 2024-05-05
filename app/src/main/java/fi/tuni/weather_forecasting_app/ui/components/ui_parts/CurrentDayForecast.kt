package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel

@Composable
fun CurrentDayForecast(date: String, pageOffsetFraction: Float) {

    // model for forecast
    val forecastViewModel : WeatherDataViewModel = viewModel()
    val hourlyData =  forecastViewModel.getHourlyData(date)

    if (hourlyData.isEmpty() || pageOffsetFraction != 0F) {
        // show loading
        Text(text = "Loading...", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    } else {
        LazyColumn {
            items(hourlyData.size) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = hourlyData[it].hour,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "${hourlyData[it].temperature} °C"
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 20.dp))
            }
        }
    }
}
