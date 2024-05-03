package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.WeekDayForecast

@Composable
fun HomeScreen(navController : NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Divider()
        WeekDayForecast()
    }
}