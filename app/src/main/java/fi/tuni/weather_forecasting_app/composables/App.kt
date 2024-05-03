package fi.tuni.weather_forecasting_app.composables

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fi.tuni.weather_forecasting_app.composables.screens.HomeScreen

@Composable
fun App() {
    // request permissions for the app
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            // Check if all requested permissions have been granted
            val allPermissionsGranted = permissions.entries.all { it.value }
        }
    )
    // Define permissions based on API level
    val permissions = mutableListOf<String>()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions.add(Manifest.permission.POST_NOTIFICATIONS)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        permissions.add(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        permissions.add(Manifest.permission.FOREGROUND_SERVICE)
    }

    // Location permissions are required for all API levels
    permissions.addAll(listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ))

    // Launch the permission request
    LaunchedEffect(Unit) {
        permissionLauncher.launch(permissions.toTypedArray())
    }

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home-screen") {
        composable("home-screen") {
            // Navigate to home screen
            HomeScreen(navController)
        }
    }
}