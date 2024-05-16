package fi.tuni.weather_forecasting_app.ui.components.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.CurrentWeatherDisplay
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.NavBar
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.WeekSelector
import fi.tuni.weather_forecasting_app.ui.theme.IndigoGradientBackground
import fi.tuni.weather_forecasting_app.viewmodels.NavigationItemsViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun HomeScreen(
    navController : NavController,
    navigationItemsViewModel: NavigationItemsViewModel,
    weekViewModel: WeekDayViewModel,
    weatherDataViewModel: WeatherDataViewModel
) {

    // Navigation bar
    NavBar(navController, navigationItemsViewModel, "Home", "home") {

        // Content of the screen
        IndigoGradientBackground {
            Column(modifier = Modifier.fillMaxSize()) {

                // Forecasts
                WeekSelector(navController)

                // Current weather
                CurrentWeatherDisplay(weatherDataViewModel)
            }
        }
    }
}