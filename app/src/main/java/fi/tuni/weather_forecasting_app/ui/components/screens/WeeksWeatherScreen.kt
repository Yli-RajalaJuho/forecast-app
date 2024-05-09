package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.ForecastOfTheWeek
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.NavBar
import fi.tuni.weather_forecasting_app.ui.theme.IndigoGradientBackground
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun WeeksWeatherScreen(
    navController: NavController,
    weekViewModel: WeekDayViewModel,
    weatherDataViewModel: WeatherDataViewModel,
    week: String?
) {

    // Determine which week to display based on the week parameter
    val displayWeek = when (week) {
        "Previous" -> weekViewModel.previousWeek
        "Next" -> weekViewModel.nextWeek
        "Current" -> weekViewModel.currentWeek
        else -> weekViewModel.currentWeek
    }

    IndigoGradientBackground {
        Column {
            NavBar("Forecasts", "$week week")
            ForecastOfTheWeek(displayWeek, weekViewModel, weatherDataViewModel)
        }
    }
}
