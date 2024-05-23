package fi.tuni.weather_forecasting_app.ui.components.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.ui.components.ui_parts.NavBar
import fi.tuni.weather_forecasting_app.viewmodels.NavigationItemsViewModel
import fi.tuni.weather_forecasting_app.viewmodels.SettingsViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel
import java.util.Locale

@Composable
fun SettingsScreen(
    settings: SettingsViewModel,
    navController : NavController,
    navigationItemsViewModel: NavigationItemsViewModel,
    weekViewModel: WeekDayViewModel,
    weatherDataViewModel: WeatherDataViewModel
) {

    val theme by settings.theme.collectAsState()
    val tempUnit by settings.temperatureUnit.collectAsState()
    val windSpeedUnit by settings.windSpeedUnit.collectAsState()

    // Observe changes in temperature unit and update WeatherDataViewModel
    LaunchedEffect(tempUnit) {
        weatherDataViewModel.setTemperatureUnit(tempUnit)
    }

    // Observe changes in wind speed unit and update WeatherDataViewModel
    LaunchedEffect(windSpeedUnit) {
        weatherDataViewModel.setWindSpeedUnit(windSpeedUnit)
    }

    // Color scheme for radio buttons
    val radioButtonColors = RadioButtonColors(
        selectedColor = Color.Transparent.compositeOver(
            MaterialTheme.colorScheme.onPrimary
        ),
        unselectedColor = Color.Transparent.compositeOver(
            MaterialTheme.colorScheme.onPrimary
        ).copy(alpha = 0.5f),
        disabledSelectedColor = Color.Transparent.compositeOver(
            MaterialTheme.colorScheme.onPrimary
        ),
        disabledUnselectedColor = Color.Transparent.compositeOver(
            MaterialTheme.colorScheme.onPrimary
        )
    )

    // Remember the currently expanded item index
    var expandedItem by remember { mutableStateOf("") }

    BackHandler {
        navController.navigate("home-screen")
    }

    // Navigation bar
    NavBar(navController, navigationItemsViewModel, "Settings") {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Transparent.compositeOver(
                        MaterialTheme.colorScheme.primary
                    )
                )
        ) {

            // Theme control
            item {
                Box(modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .clickable {
                        expandedItem = if (expandedItem == "theme") "" else "theme"
                    }
                    .background(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.secondary
                        ).copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp),) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .weight(1f),
                                text = "Theme",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onPrimary
                                )
                            )

                            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = theme.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.ROOT
                                        ) else it.toString()
                                    },
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(10.dp),
                            color = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onPrimary
                            )
                        )

                        if (expandedItem == "theme") {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Default",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onPrimary
                                        )
                                    )

                                    RadioButton(
                                        selected = theme == "default",
                                        onClick = { settings.setTheme("default") },
                                        colors = radioButtonColors
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Light",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onPrimary
                                        )
                                    )

                                    RadioButton(
                                        selected = theme == "light",
                                        onClick = { settings.setTheme("light") },
                                        colors = radioButtonColors
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Dark",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onPrimary
                                        )
                                    )

                                    RadioButton(
                                        selected = theme == "dark",
                                        onClick = { settings.setTheme("dark") },
                                        colors = radioButtonColors
                                    )
                                }
                            }

                            Text(
                                modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                                text = "(Default is the device's current theme.)",
                                fontSize = 12.sp,
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }

            // Temperature unit control
            item {
                Box(modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .clickable {
                        expandedItem =
                            if (expandedItem == "temperatureUnit") "" else "temperatureUnit"
                    }
                    .background(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.secondary
                        ).copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp),) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .weight(1f),
                                text = "Temperature",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onPrimary
                                )
                            )

                            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = tempUnit.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.ROOT
                                        ) else it.toString()
                                    },
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(10.dp),
                            color = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onPrimary
                            )
                        )

                        if (expandedItem == "temperatureUnit") {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Celsius (°C)",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onPrimary
                                        )
                                    )

                                    RadioButton(
                                        selected = tempUnit == "celsius",
                                        onClick = { settings.setTemperatureUnit("celsius") },
                                        colors = radioButtonColors
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Fahrenheit (°F)",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onPrimary
                                        )
                                    )

                                    RadioButton(
                                        selected = tempUnit == "fahrenheit",
                                        onClick = { settings.setTemperatureUnit("fahrenheit") },
                                        colors = radioButtonColors
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Wind speed unit control
            item {
                Box(modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .clickable {
                        expandedItem = if (expandedItem == "windSpeedUnit") "" else "windSpeedUnit"
                    }
                    .background(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.secondary
                        ).copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp),) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .weight(1f),
                                text = "Wind speed",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onPrimary
                                )
                            )

                            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = windSpeedUnit,
                                    fontSize = 16.sp,
                                    color = Color.Transparent.compositeOver(
                                        MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(10.dp),
                            color = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onPrimary
                            )
                        )

                        if (expandedItem == "windSpeedUnit") {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "m/s",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onPrimary
                                        )
                                    )

                                    RadioButton(
                                        selected = windSpeedUnit == "ms",
                                        onClick = { settings.setWindSpeedUnit("ms") },
                                        colors = radioButtonColors
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "mph",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Transparent.compositeOver(
                                            MaterialTheme.colorScheme.onPrimary
                                        )
                                    )

                                    RadioButton(
                                        selected = windSpeedUnit == "mph",
                                        onClick = { settings.setWindSpeedUnit("mph") },
                                        colors = radioButtonColors
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}