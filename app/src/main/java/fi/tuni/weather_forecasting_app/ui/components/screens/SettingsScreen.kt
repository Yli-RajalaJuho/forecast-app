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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

            item {
                Box(modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .clickable {
                        expandedItem = if (expandedItem == "theme") "" else "theme"
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        //horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 20.dp),
                                text = "Theme",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onPrimary
                                )
                            )

                            Text(
                                modifier = Modifier.padding(end = 40.dp),
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

                        HorizontalDivider(
                            modifier = Modifier.padding(10.dp),
                            color = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onPrimary
                            )
                        )

                        if (expandedItem == "theme") {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Default", fontWeight = FontWeight.Bold)

                                    RadioButton(
                                        selected = theme == "default",
                                        onClick = { settings.setTheme("default") },
                                        colors = RadioButtonColors(
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
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Light", fontWeight = FontWeight.Bold)

                                    RadioButton(
                                        selected = theme == "light",
                                        onClick = { settings.setTheme("light") },
                                        colors = RadioButtonColors(
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
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Dark", fontWeight = FontWeight.Bold)

                                    RadioButton(
                                        selected = theme == "dark",
                                        onClick = { settings.setTheme("dark") },
                                        colors = RadioButtonColors(
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
        }
    }
}