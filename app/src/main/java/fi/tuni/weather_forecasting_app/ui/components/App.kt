package fi.tuni.weather_forecasting_app.ui.components

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fi.tuni.weather_forecasting_app.ui.components.screens.HomeScreen
import fi.tuni.weather_forecasting_app.ui.components.screens.SettingsScreen
import fi.tuni.weather_forecasting_app.ui.components.screens.WeeksWeatherScreen
import fi.tuni.weather_forecasting_app.viewmodels.NavigationItemsViewModel
import fi.tuni.weather_forecasting_app.viewmodels.SettingsViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun App(settings: SettingsViewModel) {

    val context = LocalContext.current

    // Check if network is available
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    // Remember the view models outside of the composable
    val weekViewModel: WeekDayViewModel = viewModel()
    val navigationItemsViewModel: NavigationItemsViewModel = viewModel()
    val weatherDataViewModel: WeatherDataViewModel = viewModel()


    // request permissions for the app
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            // Check if all requested permissions have been granted
            val allPermissionsGranted = permissions.entries.all { it.value }

            if (allPermissionsGranted && isNetworkAvailable()) {
                // Permissions granted, proceed with refreshing weather data
                weatherDataViewModel.refreshWeatherData()
            }
        }
    )
    // Define permissions based on API level
    val permissions = mutableListOf<String>()

    // For foreground service and notifications
    /*
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions.add(Manifest.permission.POST_NOTIFICATIONS)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        permissions.add(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        permissions.add(Manifest.permission.FOREGROUND_SERVICE)
    }
     */

    // Location permissions are required for all API levels
    permissions.addAll(listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ))

    // Launch the permission request
    LaunchedEffect(Unit) {
        permissionLauncher.launch(permissions.toTypedArray())
    }

    // Get the settings and change the values of weather data viewmodel based on them
    val tempUnit by settings.temperatureUnit.collectAsState()
    val windSpeedUnit by settings.windSpeedUnit.collectAsState()
    val precipitationUnit by settings.precipitationUnit.collectAsState()

    if (weatherDataViewModel.tempUnit.value != tempUnit) {
        weatherDataViewModel.setTemperatureUnit(tempUnit)
    }

    if (weatherDataViewModel.windSpeedUnit.value != windSpeedUnit) {
        weatherDataViewModel.setWindSpeedUnit(windSpeedUnit)
    }

    if (weatherDataViewModel.precipitationUnit.value != precipitationUnit) {
        weatherDataViewModel.setPrecipitationUnit(precipitationUnit)
    }

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home-screen") {
        composable("home-screen") {
            // Navigate to home screen
            HomeScreen(
                settings,
                navController,
                navigationItemsViewModel,
                weekViewModel,
                weatherDataViewModel
            )
        }

        composable("weeks-weather-screen/{week}") {backStackEntry ->

            // previous, current or next week
            val week = backStackEntry.arguments?.getString("week")

            // Navigate to weeks weather screen (forecast) with the week and the view models
            WeeksWeatherScreen(
                settings,
                navController,
                navigationItemsViewModel,
                weekViewModel,
                weatherDataViewModel,
                week
            )
        }

        composable("settings-screen") {
            // Navigate to settings screen
            SettingsScreen(
                settings,
                navController,
                navigationItemsViewModel,
                weekViewModel,
                weatherDataViewModel
            )
        }
    }
}
