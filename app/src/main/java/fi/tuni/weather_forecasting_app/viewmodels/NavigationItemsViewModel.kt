package fi.tuni.weather_forecasting_app.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.tuni.weather_forecasting_app.models.NavigationItem
import fi.tuni.weather_forecasting_app.models.NavigationItems
import kotlinx.coroutines.launch

class NavigationItemsViewModel: ViewModel() {

    // navigation items
    private val _navigationItems: SnapshotStateList<NavigationItem> =
        mutableStateListOf<NavigationItem>()
    val navigationItems: List<NavigationItem> get() = _navigationItems


    init {
        viewModelScope.launch {
            _navigationItems.addAll(NavigationItems().navigationItems)
        }
    }
}
