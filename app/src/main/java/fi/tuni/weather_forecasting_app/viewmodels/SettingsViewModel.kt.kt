package fi.tuni.weather_forecasting_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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

    // Wind speed unit
    private val _windSpeedUnit = MutableStateFlow("ms")
    val windSpeedUnit = _windSpeedUnit

    // Method to update the theme mode in the DataStore
    fun setTheme(newTheme: String) {
        viewModelScope.launch {
            preferencesManager.saveThemeMode(newTheme)
        }
    }

    // Method to update the temperature unit in the DataStore
    fun setTemperatureUnit(newUnit: String) {
        viewModelScope.launch {
            preferencesManager.saveTemperatureUnit(newUnit)
        }
    }

    // Method to update the wind speed unit in the DataStore
    fun setWindSpeedUnit(newUnit: String) {
        viewModelScope.launch {
            preferencesManager.saveWindSpeedUnit(newUnit)
        }
    }

    init {
        // Observe changes in theme mode and update the StateFlow
        viewModelScope.launch {

            preferencesManager.themeModeFlow.collect { themeMode ->
                _theme.value = themeMode
            }
        }

        // Observe changes in temperature unit and update the StateFlow
        viewModelScope.launch {

            preferencesManager.temperatureUnitFlow.collect { temperatureUnit ->
                _temperatureUnit.value = temperatureUnit
            }
        }

        // Observe changes in wind speed unit and update the StateFlow
        viewModelScope.launch {

            preferencesManager.windSpeedUnitFlow.collect { windSpeedUnit ->
                _windSpeedUnit.value = windSpeedUnit
            }
        }
    }
}
