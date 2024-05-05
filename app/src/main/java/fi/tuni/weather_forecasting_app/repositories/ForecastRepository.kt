package fi.tuni.weather_forecasting_app.repositories

import fi.tuni.weather_forecasting_app.models.SimplifiedWeatherData
import fi.tuni.weather_forecasting_app.models.WeatherData
import fi.tuni.weather_forecasting_app.models.WeatherHourly
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface WeatherApiService {
    @GET("forecast")
    suspend fun getInitialWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String,
        @Query("past_days") past: Int,
        @Query("forecast_days") forecast: Int
    ): WeatherData
}

object ForecastRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getInitialWeatherForecast(
        latitude: Double, longitude: Double, hourly: String, past: Int, forecast: Int): WeatherData {
        return service.getInitialWeatherForecast(latitude, longitude, hourly, past, forecast)
    }

    fun generateSimplifiedData(weatherData: WeatherHourly?): List<SimplifiedWeatherData>? {
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
}
