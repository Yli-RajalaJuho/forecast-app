package fi.tuni.weather_forecasting_app.viewmodels

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fi.tuni.weather_forecasting_app.models.SimplifiedWeatherData
import fi.tuni.weather_forecasting_app.repositories.ForecastRepository
import fi.tuni.weather_forecasting_app.repositories.LocationClient
import fi.tuni.weather_forecasting_app.repositories.LocationRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class WeatherDataViewModel(
    application: Application,
    //private val settingsViewModel: SettingsViewModel
): AndroidViewModel(application) {

    private val locationRepository = LocationRepository(application)

    // Current forecast data
    private val _forecastData: MutableState<List<SimplifiedWeatherData>?> = mutableStateOf(null)
    val forecastData: List<SimplifiedWeatherData> get() = _forecastData.value ?: emptyList()

    // Current weather data
    private val _currentWeatherData: MutableState<SimplifiedWeatherData?> = mutableStateOf(null)
    val currentWeatherData: SimplifiedWeatherData? get() = _currentWeatherData.value

    // Refreshing state
    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing get() = _isRefreshing

    // What current data to fetch (changed from the settings)
    private val _initialCurrentFetch = "temperature_2m,apparent_temperature,weather_code,wind_speed_10m"
    private val _currentDataToFetch: MutableState<String?> = mutableStateOf(_initialCurrentFetch)
    val currentDataToFetch get() = _currentDataToFetch

    // What hourly data to fetch (changed from the settings)
    private val _initialHourlyFetch = "temperature_2m,weather_code"
    private val _hourlyDataToFetch: MutableState<String?> = mutableStateOf(_initialHourlyFetch)
    val hourlyDataToFetch get() = _hourlyDataToFetch

    // Returns a list generated from the forecastData based on given date
    fun getHourlyData(date: String): List<SimplifiedWeatherData> {
        val dataForDay: MutableList<SimplifiedWeatherData> = mutableListOf<SimplifiedWeatherData>()

        _forecastData.value?.forEach() {
            if (it.date == date) {
                dataForDay.add(it)
            }
        }

        return dataForDay
    }

    fun refreshWeatherData() {
        if (_isRefreshing.value) {
            Log.d("WEATHER", "Fetching already in progress!")
            return
        }

        // If process is not already active
        Log.d("WEATHER", "Starting to fetch weather")
        _isRefreshing.value = true

        viewModelScope.launch {
            val currentLocation: Location = fetchLocation()

            try {
                // fetch data with the location
                val response = ForecastRepository.service.getWeatherForecast(
                    currentLocation.latitude,
                    currentLocation.longitude,
                    _currentDataToFetch.value ?: _initialCurrentFetch,
                    _hourlyDataToFetch.value ?: _initialHourlyFetch,
                    14,
                    14,
                    "ms"
                )

                _forecastData.value = ForecastRepository.generateSimplifiedHourlyData(response.hourly)
                _currentWeatherData.value = ForecastRepository.generateSimplifiedCurrentData(response.current)

            } catch (e: HttpException) {
                Log.d("HTTP ERROR", e.response()?.errorBody()?.string() ?: "")
            } finally {
                Log.d("WEATHER", "Weather fetching complete")
                _isRefreshing.value = false
            }
        }
    }

    private suspend fun fetchLocation(): Location {
        return withContext(Dispatchers.Default) {
            val currentLocationDeferred = CompletableDeferred<Location>()

            viewModelScope.launch {
                try {
                    locationRepository.startLocationUpdates { location ->
                        if (location != null) {
                            locationRepository.stopLocationUpdates()
                            currentLocationDeferred.complete(location)
                        }
                    }
                } catch (e: LocationClient.LocationException) {
                    e.message?.let { Log.d("LOCATION ERROR", it) }
                    currentLocationDeferred.completeExceptionally(e)
                }
            }

            currentLocationDeferred.await()
        }
    }

    init {
        // Initial weather based on current location
        refreshWeatherData()
    }
}
