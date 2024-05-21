package fi.tuni.weather_forecasting_app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesManager(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    companion object {
        val THEME_MODE_KEY = stringPreferencesKey("THEME_MODE")
        val TEMPERATURE_UNIT_KEY = stringPreferencesKey("TEMPERATURE_UNIT")
        val WIND_SPEED_UNIT_KEY = stringPreferencesKey("WIND_SPEED_UNIT")
    }

    val themeModeFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[THEME_MODE_KEY] ?: "default"
    }

    val temperatureUnitFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[TEMPERATURE_UNIT_KEY] ?: "celsius"
    }

    val windSpeedUnitFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[WIND_SPEED_UNIT_KEY] ?: "ms"
    }

    suspend fun saveThemeMode(themeMode: String) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode
        }
    }

    suspend fun saveTemperatureUnit(temperatureUnit: String) {
        dataStore.edit { preferences ->
            preferences[TEMPERATURE_UNIT_KEY] = temperatureUnit
        }
    }

    suspend fun saveWindSpeedUnit(windSpeedUnit: String) {
        dataStore.edit { preferences ->
            preferences[WIND_SPEED_UNIT_KEY] = windSpeedUnit
        }
    }
}