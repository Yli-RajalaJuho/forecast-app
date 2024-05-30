package fi.tuni.weather_forecasting_app.ui.components.ui_parts

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
import androidx.compose.foundation.layout.offset
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
fun CurrentWeatherDisplay(weatherDataViewModel: WeatherDataViewModel) {

    // model for forecast
    val isRefreshing = remember { weatherDataViewModel.isRefreshing }

    val currentData = remember(weatherDataViewModel.currentWeatherData ) {
        weatherDataViewModel.currentWeatherData
    }

    var expandedMoreInfo by remember { mutableStateOf(false) }

    val alphaData = animateFloatAsState(
        targetValue = if (currentData != null) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = ""
    )

    val pullToRefreshState = rememberPullToRefreshState(positionalThreshold = PullToRefreshDefaults.PositionalThreshold * 0.6f)

    // Alpha value of the info boxes
    val infoBoxAlpha = 0.75f

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
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                        .background(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.Transparent
                                .compositeOver(
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                                .copy(alpha = infoBoxAlpha)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "No Weather Data Available",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        color = Color.Transparent
                            .compositeOver(
                                MaterialTheme.colorScheme.onSecondaryContainer
                            )
                    )

                    Text(
                        text = "Please check internet connectivity and allow permissions for location updates and launch the app again.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        color = Color.Transparent
                            .compositeOver(
                                MaterialTheme.colorScheme.onSecondaryContainer
                            )
                    )
                }
            }
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
                                text = "${currentData.temperature} " +
                                        if (weatherDataViewModel.tempUnit.value == "fahrenheit") "°F"
                                        else "°C",
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
                                text = "Feels like ${currentData.apparentTemperature} " +
                                        if (weatherDataViewModel.tempUnit.value == "fahrenheit") "°F"
                                        else "°C",
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
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .offset(y = (-20).dp),
                            containerColor = Color.Transparent.compositeOver(MaterialTheme.colorScheme.primary),
                            contentColor = Color.Transparent.compositeOver(MaterialTheme.colorScheme.onPrimary),
                        )
                    }
                }


                // Weather conditions
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
                        ),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // Weather conditions
                        Text(
                            text = currentData.weatherCode.conditions,
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterVertically)
                                .padding(start = 20.dp),
                            textAlign = TextAlign.Start,
                            fontSize = 24.sp,
                            color = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onSecondaryContainer)
                        )

                        // Weather code icon
                        Icon(
                            painter = painterResource(id = currentData.weatherCode.weatherIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                                .padding(vertical = 20.dp)
                                .size(40.dp),
                            tint = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onSecondaryContainer)
                        )
                    }
                }

                // More info
                item {

                    Column(modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .clickable {
                            // Toggle the expanded state
                            expandedMoreInfo = !expandedMoreInfo
                        }
                        .fillMaxWidth()
                        .background(
                            color = Color.Transparent
                                .compositeOver(
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                                .copy(
                                    alpha = infoBoxAlpha
                                )
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "More Info",
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }

                        if (expandedMoreInfo) {

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )

                            // Wind
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Wind icon
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_air),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp)
                                        .size(30.dp),
                                    tint = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(2f)
                                        .fillMaxWidth()
                                        .padding(vertical = 20.dp),
                                ) {
                                    // Wind speed in m/s
                                    Row {
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = "Speed",
                                            textAlign = TextAlign.Start,
                                            fontSize = 16.sp,
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        )

                                        // Wind speed in m/s
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = "${currentData.windSpeed} " +
                                                    if (weatherDataViewModel.windSpeedUnit.value == "mph") "mph"
                                                    else "m/s",
                                            textAlign = TextAlign.Start,
                                            fontSize = 16.sp,
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
                                            fontSize = 16.sp,
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        )

                                        // Wind direction
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = "${
                                                weatherDataViewModel.getWindDirection(
                                                    currentData.windDirection
                                                )
                                            } (${currentData.windDirection} °)",
                                            textAlign = TextAlign.Start,
                                            fontSize = 16.sp,
                                            color = Color.Transparent.compositeOver(
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        )
                                    }
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )

                            // Cloud cover
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Cloud icon
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_cloud),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp)
                                        .size(30.dp),
                                    tint = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )

                                // Cover
                                Text(
                                    text = "Cloud cover",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp),
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )

                                // Cover %
                                Text(
                                    text = "${currentData.cloudCover} %",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp),
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )

                            // Visibility
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Visibility icon
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_eye),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp)
                                        .size(30.dp),
                                    tint = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )

                                // Visibility
                                Text(
                                    text = "Visibility",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp),
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )

                                // visibility in converted to km
                                Text(
                                    text =
                                    if (weatherDataViewModel.precipitationUnit.value == "mm")
                                        "${String.format("%.2f", currentData.visibility * 0.001)} km"
                                    else "${String.format("%.2f", currentData.visibility * 0.0001894)} mi",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp),
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )

                            // UV index
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Sun icon
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_wb_sunny),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp)
                                        .size(30.dp),
                                    tint = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )

                                // UV index
                                Text(
                                    text = "UV Index",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp),
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )

                                // index
                                Text(
                                    text = "${currentData.uvIndex}",
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterVertically)
                                        .padding(vertical = 20.dp),
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )
                            }
                        }
                    }
                }
                
                item { 
                    Spacer(modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp))
                }
            }
        }
    }
}
