package fi.tuni.weather_forecasting_app.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: String? = null,
    val weekInfo: String? = null
)

data class NavigationItems(
    val navigationItems: List<NavigationItem> = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "home-screen"
        ),
        NavigationItem(
            title = "Forecast next week",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = "weeks-weather-screen/Next",
            weekInfo = "Next"
        ),
        NavigationItem(
            title = "Forecast current week",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = "weeks-weather-screen/Current",
            weekInfo = "Current"
        ),
        NavigationItem(
            title = "Forecast previous week",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = "weeks-weather-screen/Previous",
            weekInfo = "Previous"
        ),
        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "settings-screen"
        )
    )
)
