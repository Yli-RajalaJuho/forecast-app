package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.ForecastOfTheWeek

@Composable
fun HomeScreen(navController : NavController) {

    Column(modifier = Modifier.fillMaxSize()) {
        ForecastOfTheWeek(navController)
    }
}