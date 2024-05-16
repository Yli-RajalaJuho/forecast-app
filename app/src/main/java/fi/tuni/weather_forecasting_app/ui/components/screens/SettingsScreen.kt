package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.NavBar
import fi.tuni.weather_forecasting_app.ui.theme.IndigoGradientBackground
import fi.tuni.weather_forecasting_app.viewmodels.NavigationItemsViewModel
import fi.tuni.weather_forecasting_app.viewmodels.SettingsViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun SettingsScreen(
    settings: SettingsViewModel,
    navController : NavController,
    navigationItemsViewModel: NavigationItemsViewModel,
    weekViewModel: WeekDayViewModel,
    weatherDataViewModel: WeatherDataViewModel
) {

    val theme by settings.theme.collectAsState()

    BackHandler {
        navController.navigate("home-screen")
    }

    // Navigation bar
    NavBar(navController, navigationItemsViewModel, "Settings") {

        // Content of the screen
        IndigoGradientBackground(settings) {
            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = { settings.setTheme("default") }) {
                    Text(text = "default")

                }
                Button(onClick = { settings.setTheme("dark") }) {
                    Text(text = "dark")

                }
                Button(onClick = { settings.setTheme("light") }) {
                    Text(text = "light")

                }
            }
        }
    }
}