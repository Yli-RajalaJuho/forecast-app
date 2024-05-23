package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import android.icu.util.Calendar
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastOfTheDay(
    date: String,
    opacity: Float,
    forecastViewModel: WeatherDataViewModel
) {

    // model for forecast
    val isRefreshing = remember { forecastViewModel.isRefreshing }

    var hourlyData = remember(forecastViewModel.forecastData) {
        forecastViewModel.getHourlyData(date)
    }

    // Remember the currently expanded item index
    var expandedItemIndex by remember { mutableStateOf(-1) }

    val alphaData = animateFloatAsState(
        targetValue = if (hourlyData.isNotEmpty()) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = ""
    )

    // Call getHourlyData when the data inside viewmodel changes
    LaunchedEffect(forecastViewModel.forecastData) {
        hourlyData = forecastViewModel.getHourlyData(date)
    }

    val pullToRefreshState = rememberPullToRefreshState(
        positionalThreshold = PullToRefreshDefaults.PositionalThreshold * 0.6f)

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    )

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            forecastViewModel.refreshWeatherData()
        }
    }

    LaunchedEffect(isRefreshing.value) {
        if (isRefreshing.value) {
            pullToRefreshState.startRefresh()
        } else {
            pullToRefreshState.endRefresh()
        }
    }

    if (hourlyData.isEmpty()) {
        if (!isRefreshing.value) {
            // show no data
            Text(
                text = "No Weather Data Available",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    } else {
        Box(modifier = Modifier.alpha(opacity)) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    .alpha(alphaData.value),
            ) {
                items(hourlyData.size) {

                    Box(
                        modifier = Modifier
                            .padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 15.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .clickable {
                                // Toggle the expanded state
                                expandedItemIndex = if (expandedItemIndex == it) -1 else it
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Column {
                            Box(
                                modifier = Modifier.height(120.dp)
                            ) {
                                // Background
                                Image(
                                    painter = painterResource(id = hourlyData[it].weatherCode.backgroundImage),
                                    contentDescription = "background image",
                                    modifier = Modifier
                                        .size(LocalConfiguration.current.screenWidthDp.dp)
                                        .graphicsLayer(alpha = alphaData.value)
                                        .animateContentSize()
                                        .alpha(0.5f),
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center,
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = Color.Transparent
                                                .compositeOver(
                                                    MaterialTheme.colorScheme.secondaryContainer
                                                )
                                                .copy(alpha = 0.5f)
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    // Hour
                                    Text(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 10.dp),
                                        textAlign = TextAlign.Start,
                                        text = hourlyData[it].hour,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onSecondaryContainer)
                                    )
                                    
                                    // Temperature and weather condition
                                    Column(
                                        modifier = Modifier.weight(2f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            text = hourlyData[it].weatherCode.conditions,
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer)
                                        )
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            text = "${hourlyData[it].temperature} " +
                                                    if (forecastViewModel.tempUnit.value == "fahrenheit") "°F"
                                                    else "°C",
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer)
                                        )
                                    }

                                    // Weather condition icon
                                    Icon(
                                        painter = painterResource(id = hourlyData[it].weatherCode.weatherIcon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(1f)
                                            .align(alignment = Alignment.CenterVertically)
                                            .padding(20.dp),
                                        tint = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onSecondaryContainer)
                                    )
                                }

                                if (expandedItemIndex != it) {
                                    Text(
                                        text = "more details",
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        ),
                                        modifier = Modifier
                                            .padding(bottom = 5.dp)
                                            .align(
                                                Alignment.BottomCenter
                                            )
                                    )
                                }
                            }

                            // Display additional details if this item is expanded
                            if (expandedItemIndex == it) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = Color.Transparent
                                                .compositeOver(
                                                    MaterialTheme.colorScheme.secondaryContainer
                                                )
                                                .copy(alpha = 0.5f)
                                        )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(10.dp)
                                    ) {
                                        Spacer(modifier = Modifier.padding(5.dp))

                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            
                                            Spacer(modifier = Modifier.weight(1f))

                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "Feels like",
                                                textAlign = TextAlign.Start,
                                                fontSize = 12.sp,
                                                color = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )

                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "${hourlyData[it].apparentTemperature} " +
                                                        if (forecastViewModel.tempUnit.value == "fahrenheit") "°F"
                                                        else "°C",
                                                textAlign = TextAlign.Start,
                                                fontSize = 12.sp,
                                                color = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )
                                        }

                                        HorizontalDivider(
                                            modifier = Modifier.padding(20.dp),
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        )

                                        Row {
                                            // Wind
                                            Icon(
                                                painter = painterResource(id = R.drawable.outline_air),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .align(alignment = Alignment.CenterVertically),
                                                tint = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )

                                            Column(
                                                modifier = Modifier
                                                    .weight(2f)
                                                    .fillMaxWidth()
                                            ) {
                                                // Wind speed in m/s
                                                Row {
                                                    Text(
                                                        modifier = Modifier.weight(1f),
                                                        text = "Speed",
                                                        textAlign = TextAlign.Start,
                                                        fontSize = 12.sp,
                                                        color = Color.Transparent.compositeOver(
                                                            MaterialTheme.colorScheme.onSecondaryContainer
                                                        )
                                                    )

                                                    // Wind speed in m/s
                                                    Text(
                                                        modifier = Modifier.weight(1f),
                                                        text = "${hourlyData[it].windSpeed} " +
                                                                if (forecastViewModel.windSpeedUnit.value == "mph") "mph"
                                                                else "m/s",
                                                        textAlign = TextAlign.Start,
                                                        fontSize = 12.sp,
                                                        color = Color.Transparent.compositeOver(
                                                            MaterialTheme.colorScheme.onSecondaryContainer
                                                        )
                                                    )
                                                }

                                                Spacer(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(5.dp))

                                                // Wind direction
                                                Row {
                                                    Text(
                                                        modifier = Modifier.weight(1f),
                                                        text = "Direction",
                                                        textAlign = TextAlign.Start,
                                                        fontSize = 12.sp,
                                                        color = Color.Transparent.compositeOver(
                                                            MaterialTheme.colorScheme.onSecondaryContainer
                                                        )
                                                    )

                                                    // Wind direction
                                                    Text(
                                                        modifier = Modifier.weight(1f),
                                                        text = "${
                                                            forecastViewModel.getWindDirection(
                                                                hourlyData[it].windDirection
                                                            )
                                                        } (${hourlyData[it].windDirection} °)",
                                                        textAlign = TextAlign.Start,
                                                        fontSize = 12.sp,
                                                        color = Color.Transparent.compositeOver(
                                                            MaterialTheme.colorScheme.onSecondaryContainer
                                                        )
                                                    )
                                                }
                                            }
                                        }

                                        HorizontalDivider(
                                            modifier = Modifier.padding(20.dp),
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        )

                                        Row {
                                            // Cloud cover
                                            Icon(
                                                painter = painterResource(id = R.drawable.outline_cloud),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .align(alignment = Alignment.CenterVertically),
                                                tint = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )

                                            // Cloud cover
                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "Cloud cover",
                                                textAlign = TextAlign.Start,
                                                fontSize = 12.sp,
                                                color = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )

                                            // Cloud cover %
                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "${hourlyData[it].cloudCover} %",
                                                textAlign = TextAlign.Start,
                                                fontSize = 12.sp,
                                                color = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )
                                        }

                                        HorizontalDivider(
                                            modifier = Modifier.padding(20.dp),
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        )

                                        Row {
                                            // Visibility
                                            Icon(
                                                painter = painterResource(id = R.drawable.outline_eye),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .align(alignment = Alignment.CenterVertically),
                                                tint = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )

                                            // Visibility
                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "Visibility",
                                                textAlign = TextAlign.Start,
                                                fontSize = 12.sp,
                                                color = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )

                                            // Visibility converted to km
                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "${hourlyData[it].visibility * 0.001} km",
                                                textAlign = TextAlign.Start,
                                                fontSize = 12.sp,
                                                color = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )
                                        }

                                        HorizontalDivider(
                                            modifier = Modifier.padding(20.dp),
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        )

                                        Row {
                                            // UV index
                                            Icon(
                                                painter = painterResource(id = R.drawable.outline_wb_sunny),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .align(alignment = Alignment.CenterVertically),
                                                tint = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )

                                            // UV index
                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "UV Index",
                                                textAlign = TextAlign.Start,
                                                fontSize = 12.sp,
                                                color = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )

                                            // Index
                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "${hourlyData[it].uvIndex}",
                                                textAlign = TextAlign.Start,
                                                fontSize = 12.sp,
                                                color = Color.Transparent.compositeOver(
                                                    MaterialTheme.colorScheme.onSecondaryContainer
                                                )
                                            )
                                        }
                                        
                                        Spacer(modifier = Modifier.padding(10.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                containerColor = Color.Transparent.compositeOver(MaterialTheme.colorScheme.primary),
                contentColor = Color.Transparent.compositeOver(MaterialTheme.colorScheme.onPrimary),
            )
        }
    }
}
