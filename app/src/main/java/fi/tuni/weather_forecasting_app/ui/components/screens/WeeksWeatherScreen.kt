package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.ForecastOfTheWeek
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
        "previousWeek" -> weekViewModel.previousWeek
        "nextWeek" -> weekViewModel.nextWeek
        else -> weekViewModel.currentWeek
    }

    ForecastOfTheWeek(displayWeek, weekViewModel, weatherDataViewModel)
}
