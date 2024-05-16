package fi.tuni.weather_forecasting_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fi.tuni.weather_forecasting_app.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SettingsViewModel(application: Application): AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application.applicationContext)

    // Current theme
    private val _theme = MutableStateFlow("default")
    val theme = _theme

    // Temperature unit
    private val _temperatureUnit = MutableStateFlow("celsius")
    val temperatureUnit = _temperatureUnit

    // Method to update the theme mode in the DataStore
    fun setTheme(newTheme: String) {
        viewModelScope.launch {
            preferencesManager.saveThemeMode(newTheme)
        }
    }

    init {
        // Observe changes in theme mode and update the StateFlow
        viewModelScope.launch {

            preferencesManager.themeModeFlow.collect { themeMode ->
                _theme.value = themeMode
            }

            preferencesManager.temperatureUnitFlow.collect { temperatureUnit ->
                _temperatureUnit.value = temperatureUnit
            }
        }
    }
}
