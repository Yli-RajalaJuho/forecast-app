package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.ForecastOfTheWeek
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.WeekSelector
import fi.tuni.weather_forecasting_app.ui.theme.IndigoGradientBackground
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun HomeScreen(navController : NavController) {

    IndigoGradientBackground {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Transparent.compositeOver(
                    MaterialTheme.colorScheme.primaryContainer
                )
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = "Forecasts",
                textAlign = TextAlign.Center,
                color = Color.Transparent.compositeOver(
                    MaterialTheme.colorScheme.onPrimaryContainer)
            )
            WeekSelector(navController)
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Swipe left or right",
            textAlign = TextAlign.Center
        )
    }
}