package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun CurrentWeatherDisplay(
    weekDayViewModel: WeekDayViewModel,
    weatherDataViewModel: WeatherDataViewModel
) {

    // model for forecast
    val isRefreshing = remember { weatherDataViewModel.isRefreshing }

    val currentData = remember(weatherDataViewModel.currentWeatherData ) {
        weatherDataViewModel.currentWeatherData
    }

    if (isRefreshing.value) {

        // show loading
        Text(text = "Loading...", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    } else if (currentData == null) {

        // show no data
        Text(text = "No Weather Data Available", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

    } else {
        Box(modifier = Modifier.fillMaxHeight()) {
            // Background
            Image(
                painter = painterResource(id = currentData.backgroundImage),
                contentDescription = "background image",
                modifier = Modifier.size(LocalConfiguration.current.screenHeightDp.dp).alpha(0.9F),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
            )

            // Data
            Column {

                // Temperature
                Text(
                    text = "${currentData.temperature} °C",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 44.sp
                )

                // Weather conditions
                Text(
                    text = currentData.weatherConditions,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp
                )
            }
        }
    }
}
