package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel

@Composable
fun CurrentWeatherDisplay(weatherDataViewModel: WeatherDataViewModel) {

    // model for forecast
    val isRefreshing = remember { weatherDataViewModel.isRefreshing }

    val currentData = remember(weatherDataViewModel.currentWeatherData ) {
        weatherDataViewModel.currentWeatherData
    }

    val alphaData = animateFloatAsState(
        targetValue = if (currentData != null) 1f else 0f,
        animationSpec = tween(durationMillis = 2000),
        label = ""
    )

    val alphaLoadingIcon = animateFloatAsState(
        targetValue = if (isRefreshing.value) 1f else 0f,
        animationSpec = tween(durationMillis = 2000),
        label = ""
    )

    Log.d("CURRENT", currentData?.temperature.toString())

    if (currentData == null) {
        if (isRefreshing.value) {

            // Show loading
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp).alpha(alphaLoadingIcon.value),
                    color = Color.Transparent.compositeOver(MaterialTheme.colorScheme.secondary),
                )
            }
        } else {

            // show no data
            Text(text = "No Weather Data Available", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }

    } else {

        Box(modifier = Modifier.fillMaxHeight()) {
            // Background
            Image(
                painter = painterResource(id = currentData.backgroundImage),
                contentDescription = "background image",
                modifier = Modifier
                    .size(LocalConfiguration.current.screenHeightDp.dp)
                    .alpha(0.9F)
                    .graphicsLayer(alpha = alphaData.value)
                    .animateContentSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
            )

            // Data
            Column(modifier = Modifier
                .graphicsLayer(alpha = alphaData.value)
                .animateContentSize()
            ) {

                // Temperature
                Text(
                    text = "${currentData.temperature} °C",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 44.sp
                )

                // Weather conditions
                Text(
                    text = currentData.weatherConditions,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp
                )
            }

            // Loading indicator
            if (isRefreshing.value) {

                // Show loading
                Box(modifier = Modifier
                    .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp).alpha(alphaLoadingIcon.value),
                        color = Color.Transparent.compositeOver(MaterialTheme.colorScheme.secondary),
                    )
                }
            }

            // refresh button
            Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                Button(
                    onClick = {
                        weatherDataViewModel.refreshWeatherData()
                    },
                    modifier = Modifier.align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(Color.Transparent.compositeOver(
                        MaterialTheme.colorScheme.secondary))
                ) {
                    Text(
                        text = "Refresh",
                        color = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.onSecondary)
                    )
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh",
                        tint = Color.Transparent.compositeOver(MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }
        }
    }
}
