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

    private val _forecastData: MutableState<List<SimplifiedWeatherData>?> = mutableStateOf(null)
    val forecastData: List<SimplifiedWeatherData> get() = _forecastData.value ?: emptyList()

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing get() = _isRefreshing

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
                // fetch weather data with the location
                val weatherData = ForecastRepository.service.getInitialWeatherForecast(
                    currentLocation.latitude, currentLocation.longitude, "temperature_2m", 7, 14).hourly
                _forecastData.value = ForecastRepository.generateSimplifiedData(weatherData)
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
        refreshWeatherData()
    }
}
