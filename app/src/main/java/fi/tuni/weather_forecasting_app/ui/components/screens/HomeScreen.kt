package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.ForecastOfTheWeek
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun HomeScreen(navController : NavController) {

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { navController.navigate("weeks-weather-screen/currentWeek") }
        ) {
            Text("Current Week")
        }
        Button(
            onClick = { navController.navigate("weeks-weather-screen/previousWeek") }
        ) {
            Text("Previous Week")
        }
        Button(
            onClick = { navController.navigate("weeks-weather-screen/nextWeek") }
        ) {
            Text("Next Week")
        }
    }
}