package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.NavBar
import fi.tuni.weather_forecasting_app.ui.theme.IndigoGradientBackground
import fi.tuni.weather_forecasting_app.viewmodels.NavigationItemsViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun SettingsScreen(
    navController : NavController,
    navigationItemsViewModel: NavigationItemsViewModel,
    weekViewModel: WeekDayViewModel,
    weatherDataViewModel: WeatherDataViewModel
) {

    BackHandler {
        navController.navigate("home-screen")
    }

    // Navigation bar
    NavBar(navController, navigationItemsViewModel, "Settings") {

        // Content of the screen
        IndigoGradientBackground {
            Column(modifier = Modifier.fillMaxSize()) {


            }
        }
    }
}