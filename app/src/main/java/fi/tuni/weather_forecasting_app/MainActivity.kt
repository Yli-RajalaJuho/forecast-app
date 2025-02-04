package fi.tuni.weather_forecasting_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.tuni.weather_forecasting_app.ui.components.App
import fi.tuni.weather_forecasting_app.ui.theme.Weather_forecasting_appTheme
import fi.tuni.weather_forecasting_app.viewmodels.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Place the settings viewmodel on top of everything and pass it around the app
            val settings: SettingsViewModel = viewModel()

            Weather_forecasting_appTheme(settings) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(settings)
                }
            }
        }
    }
}
