package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.models.NavigationItem
import fi.tuni.weather_forecasting_app.viewmodels.NavigationItemsViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    navController: NavController,
    navigationItemsViewModel: NavigationItemsViewModel,
    header: String = "",
    description: String = "",
    content: @Composable () -> Unit
) {

    val navItems: List<NavigationItem> = navigationItemsViewModel.navigationItems

    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val week = navController.currentBackStackEntry?.arguments?.getString("week")

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Function to determine if an item is selected based on its route
    fun isItemSelected(item: NavigationItem): Boolean {
        return if (week != null) {
            item.weekInfo == week
        } else {
            item.route == currentRoute
        }
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Transparent.compositeOver(
                    MaterialTheme.colorScheme.primary
                ),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                navItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = isItemSelected(item),
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                item.route?.let {
                                    navController.navigate(item.route)
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if(item.route == currentRoute) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding
                        ),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.primary
                            ),
                            unselectedIconColor = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onPrimary
                            ),
                            unselectedBadgeColor = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onPrimary
                            ),
                            unselectedTextColor = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onPrimary
                            ),
                            selectedContainerColor = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.secondary
                            ),
                            selectedIconColor = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onSecondary
                            ),
                            selectedBadgeColor = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onSecondary
                            ),
                            selectedTextColor = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onSecondary
                            ),
                        )
                    )
                }
            }
        },
        drawerState = drawerState
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(color = Color.Transparent.compositeOver(
                        MaterialTheme.colorScheme.primary
                    )),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.primary
                        ),
                        scrolledContainerColor = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.primary
                        ),
                        navigationIconContentColor = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.primary
                        ),
                        titleContentColor = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.primary
                        ),
                        actionIconContentColor = Color.Transparent.compositeOver(
                            MaterialTheme.colorScheme.primary
                        ),
                    ),
                    title = {
                        Text(
                            textAlign = TextAlign.Center,
                            text = header,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Transparent.compositeOver(
                                MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.Transparent.compositeOver(
                                    MaterialTheme.colorScheme.onPrimary)
                            )
                        }
                    }
                )
            },
            content = {
                Box(Modifier.padding(it)) {
                    content()
                }
            }
        )
    }
}