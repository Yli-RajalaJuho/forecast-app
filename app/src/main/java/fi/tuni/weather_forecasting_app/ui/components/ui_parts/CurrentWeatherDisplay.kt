package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuni.weather_forecasting_app.R
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherDisplay(weatherDataViewModel: WeatherDataViewModel) {

    // model for forecast
    val isRefreshing = remember { weatherDataViewModel.isRefreshing }

    val currentData = remember(weatherDataViewModel.currentWeatherData ) {
        weatherDataViewModel.currentWeatherData
    }

    val alphaData = animateFloatAsState(
        targetValue = if (currentData != null) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = ""
    )

    val pullToRefreshState = rememberPullToRefreshState(positionalThreshold = PullToRefreshDefaults.PositionalThreshold * 0.6f)

    // Alpha value of the info boxes
    val infoBoxAlpha = 0.5f

    val lazyListState = rememberLazyListState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            weatherDataViewModel.refreshWeatherData()
        }
    }

    LaunchedEffect(isRefreshing.value) {
        if (isRefreshing.value) {
            pullToRefreshState.startRefresh()
        } else {
            pullToRefreshState.endRefresh()
        }
    }


    if (currentData == null) {
        if (!isRefreshing.value) {
            // show no data
            Text(
                text = "No Weather Data Available",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    } else {

        Box(modifier = Modifier.fillMaxSize()) {

            // Background
            Image(
                painter = painterResource(id = currentData.weatherCode.backgroundImage),
                contentDescription = "background image",
                modifier = Modifier
                    .size(LocalConfiguration.current.screenHeightDp.dp)
                    .graphicsLayer(alpha = alphaData.value)
                    .animateContentSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
            )

            // Data
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    .graphicsLayer(alpha = alphaData.value)
                    .animateContentSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)

            ) {

                // Temperature
                item {
                    // Pull to refresh
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .background(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.Transparent
                                .compositeOver(
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                                .copy(alpha = infoBoxAlpha)
                        )
                    ) {

                        Column(modifier = Modifier.fillMaxWidth()) {

                            // Temperature
                            Text(
                                text = "${currentData.temperature} °C",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, top = 20.dp),
                                textAlign = TextAlign.Start,
                                fontSize = 44.sp,
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onSecondaryContainer)
                            )

                            // Feels like
                            Text(
                                text = "Feels like ${currentData.apparentTemperature} °C",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, bottom = 20.dp),
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onSecondaryContainer)
                            )
                        }
                        PullToRefreshContainer(
                            state = pullToRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter).offset(y = (-20).dp),
                            containerColor = Color.Transparent.compositeOver(MaterialTheme.colorScheme.primary),
                            contentColor = Color.Transparent.compositeOver(MaterialTheme.colorScheme.onPrimary),
                        )
                    }
                }

                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.Transparent
                                .compositeOver(
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                                .copy(alpha = infoBoxAlpha)
                        )
                    ) {
                        // Weather code icon
                        Icon(
                            painter = painterResource(id = currentData.weatherCode.weatherIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                                .padding(20.dp)
                                .size(30.dp),
                            tint = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onSecondaryContainer)
                        )

                        // Weather conditions
                        Text(
                            text = currentData.weatherCode.conditions,
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterVertically)
                                .padding(20.dp),
                            textAlign = TextAlign.Start,
                            fontSize = 22.sp,
                            color = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onSecondaryContainer)
                        )
                    }
                }

                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.Transparent
                                .compositeOver(
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                                .copy(alpha = infoBoxAlpha)
                        )) {
                        Row {
                            // Wind
                            Icon(
                                painter = painterResource(id = R.drawable.outline_air),
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .align(alignment = Alignment.CenterVertically)
                                    .size(30.dp),
                                tint = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onSecondaryContainer)
                            )

                            Column(
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxWidth()
                                    .padding(20.dp),
                            ) {
                                // Wind speed in m/s
                                Text(
                                    text = "Speed ${currentData.windSpeed} m/s",
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer)
                                )

                                // Wind direction
                                Text(
                                    text = "Direction ${weatherDataViewModel.getWindDirection(currentData.windDirection)} (${currentData.windDirection} °)",
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer)
                                )
                            }
                        }
                    }
                }
                item {
                    Box(modifier = Modifier.padding(20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(20.dp))
                }

                item {
                    Box(modifier = Modifier.padding(20.dp))
                }

            }
        }
    }
}
