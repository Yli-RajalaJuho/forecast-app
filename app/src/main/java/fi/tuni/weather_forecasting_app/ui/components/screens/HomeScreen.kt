package fi.tuni.weather_forecasting_app.ui.components.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.CurrentWeatherDisplay
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.NavBar
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.WeekSelector
import fi.tuni.weather_forecasting_app.ui.theme.IndigoGradientBackground
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun HomeScreen(
    navController : NavController,
    weekViewModel: WeekDayViewModel,
    weatherDataViewModel: WeatherDataViewModel
) {

    IndigoGradientBackground {
        Column(modifier = Modifier.fillMaxSize()) {

            // Navigation bar
            NavBar("Weather")

            // Forecasts
            WeekSelector(navController)

            // Current weather
            CurrentWeatherDisplay(weatherDataViewModel)
        }
    }
}