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

class WeatherDataViewModel(application: Application): AndroidViewModel(application) {

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

    // Temperature unit
    private val _tempUnit: MutableState<String> = mutableStateOf("celsius")
    val tempUnit: MutableState<String> get() = _tempUnit

    // Wind speed unit
    private val _windSpeedUnit: MutableState<String> = mutableStateOf("ms")
    val windSpeedUnit: MutableState<String> get() = _windSpeedUnit

    // Precipitation unit
    private val _precipitationUnit: MutableState<String> = mutableStateOf("mm")
    val precipitationUnit: MutableState<String> get() = _precipitationUnit

    // What current data to fetch
    private val _initialCurrentFetch =
        "temperature_2m,apparent_temperature,weather_code,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,uv_index"
    private val _currentDataToFetch: MutableState<String?> = mutableStateOf(_initialCurrentFetch)
    val currentDataToFetch get() = _currentDataToFetch

    // What hourly data to fetch
    private val _initialHourlyFetch =
        "temperature_2m,apparent_temperature,weather_code,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,uv_index"
    private val _hourlyDataToFetch: MutableState<String?> = mutableStateOf(_initialHourlyFetch)
    val hourlyDataToFetch get() = _hourlyDataToFetch

    // Change the temperature unit
    fun setTemperatureUnit(newUnit: String) {
        if (newUnit != _tempUnit.value) {
            _tempUnit.value = newUnit
            refreshWeatherData()
        }
    }

    // Change the wind speed unit
    fun setWindSpeedUnit(newUnit: String) {
        if (newUnit != _windSpeedUnit.value) {
            _windSpeedUnit.value = newUnit
            refreshWeatherData()
        }
    }

    // Change the precipitation unit
    fun setPrecipitationUnit(newUnit: String) {
        if (newUnit != _precipitationUnit.value) {
            _precipitationUnit.value = newUnit
            refreshWeatherData()
        }
    }

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

    // Returns the wind direction
    fun getWindDirection(degrees: Int): String {
        val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW", "N")
        val index = ((degrees + 22.5) % 360 / 45).toInt()
        return directions[index]
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

            val currentLocation: Location? = fetchLocation()

            try {
                if (currentLocation != null) {
                    // fetch data with the location
                    val response = ForecastRepository.service.getWeatherForecast(
                        currentLocation.latitude,
                        currentLocation.longitude,
                        _currentDataToFetch.value ?: _initialCurrentFetch,
                        _hourlyDataToFetch.value ?: _initialHourlyFetch,
                        14,
                        14,
                        temperatureUnit = tempUnit.value,
                        windSpeedUnit = windSpeedUnit.value,
                        precipitationUnit = precipitationUnit.value
                    )

                    _forecastData.value = ForecastRepository.generateSimplifiedHourlyData(response.hourly)
                    _currentWeatherData.value = ForecastRepository.generateSimplifiedCurrentData(response.current)
                }

            } catch (e: HttpException) {
                Log.d("HTTP ERROR", e.response()?.errorBody()?.string() ?: "")
            } finally {
                Log.d("WEATHER", "Weather fetching complete")
                _isRefreshing.value = false
            }
        }
    }

    private suspend fun fetchLocation(): Location? {
        return withContext(Dispatchers.Default) {
            val currentLocationDeferred = CompletableDeferred<Location?>()

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
                    currentLocationDeferred.complete(null)
                }
            }

            currentLocationDeferred.await()
        }
    }
}
