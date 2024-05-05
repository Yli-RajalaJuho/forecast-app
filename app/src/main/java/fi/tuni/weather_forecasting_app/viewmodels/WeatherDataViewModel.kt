package fi.tuni.weather_forecasting_app.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.tuni.weather_forecasting_app.models.SimplifiedWeatherData
import fi.tuni.weather_forecasting_app.models.WeatherHourly
import fi.tuni.weather_forecasting_app.repositories.ForecastRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherDataViewModel: ViewModel() {

    private val _initialWeatherData: MutableState<List<SimplifiedWeatherData>?> = mutableStateOf(null)
    val initialWeatherData: List<SimplifiedWeatherData> get() = _initialWeatherData.value ?: emptyList()

    private fun generateSimplifiedData(weatherData: WeatherHourly?): List<SimplifiedWeatherData>? {
        val timeStamps: List<String>? = weatherData?.time
        val temperatures: List<Double>? = weatherData?.temperature_2m

        if (timeStamps != null && temperatures != null) {
            val dates = parseDates(timeStamps)
            val hours = parseHours(timeStamps)

            val myList = mutableListOf<SimplifiedWeatherData>()

            for (i in timeStamps.indices) {
                myList.add(SimplifiedWeatherData(date = dates[i], hour = hours[i], temperature = temperatures[i]))
            }

            return myList
        }

        return null
    }

    private fun parseDates(timeStamps: List<String>?): List<String> {
        val formatter: DateFormat = SimpleDateFormat.getDateInstance()
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val dateList = mutableListOf<String>()

        timeStamps?.forEach() {
            val date: Date = parser.parse(it.substringBefore("T")) ?: Date()
            val parsed = formatter.format(date)
            dateList.add(parsed)
        }

        return dateList
    }

    private fun parseHours(timeStamps: List<String>?): List<String> {
        val hoursList = mutableListOf<String>()

        timeStamps?.forEach() {
            val parsed = it.substringAfter("T")
            hoursList.add(parsed)
        }

        return hoursList
    }

    fun getHourlyData(date: String): List<SimplifiedWeatherData> {
        val dataForDay: MutableList<SimplifiedWeatherData> = mutableListOf<SimplifiedWeatherData>()

        _initialWeatherData.value?.forEach() {
            if (it.date == date) {
                dataForDay.add(it)
            }
        }

        return dataForDay
    }

    init {
        viewModelScope.launch {
            try {
                val weatherData = ForecastRepository.service.getInitialWeatherForecast(
                    50.00, 50.00, "temperature_2m", 7, 14).hourly
                _initialWeatherData.value = generateSimplifiedData(weatherData)
            } catch (e: HttpException) {
                Log.d("ERROR", e.response()?.errorBody()?.string() ?: "")
            }
        }
    }
}
