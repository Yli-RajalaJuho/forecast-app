package fi.tuni.weather_forecasting_app.repositories

import fi.tuni.weather_forecasting_app.models.SimplifiedWeatherData
import fi.tuni.weather_forecasting_app.models.WeatherCode
import fi.tuni.weather_forecasting_app.models.WeatherCodeList
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
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String,
        @Query("past_days") past: Int,
        @Query("forecast_days") forecast: Int
    ): WeatherData
}

object ForecastRepository {
    private val initialWeatherCodes = WeatherCodeList().weatherCodeList

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getWeatherForecast(
        latitude: Double, longitude: Double, hourly: String, past: Int, forecast: Int): WeatherData {
        return service.getWeatherForecast(latitude, longitude, hourly, past, forecast)
    }

    fun generateSimplifiedData(weatherData: WeatherHourly?): List<SimplifiedWeatherData>? {
        val timeStamps: List<String>? = weatherData?.time
        val temperatures: List<Double>? = weatherData?.temperature_2m
        val weatherCodes: List<Int>? = weatherData?.weather_code

        // If there is no data from the api then return null
        if (weatherData != null) {
            val dates = parseDates(timeStamps)
            val hours = parseHours(timeStamps)

            val myList = mutableListOf<SimplifiedWeatherData>()

            // Iterate over all time stamps
            for (i in timeStamps!!.indices) {

                // Fetch data related to the weather codes for the current time stamp
                var condition = ""
                var backgroundImage = 0
                for (code in initialWeatherCodes) {
                    if (weatherCodes!![i] == code.code) {
                        condition = code.conditions
                        backgroundImage = code.backgroundImage
                    }
                }

                // Try to add each new data block to the new list that contains SimplifiedWeatherData
                myList.add(
                    SimplifiedWeatherData(
                        date = dates[i],
                        hour = hours[i],
                        temperature = temperatures!![i],
                        weatherConditions = condition,
                        backgroundImage = backgroundImage
                    )
                )
            }

            // finally return the simplified weather data list
            return myList
        }

        return null
    }

    // Separate the date from the timestamps and change it into the format used by WeekDayModel
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

    // Separate the hour/minutes from the timestamps
    private fun parseHours(timeStamps: List<String>?): List<String> {
        val hoursList = mutableListOf<String>()

        timeStamps?.forEach() {
            val parsed = it.substringAfter("T")
            hoursList.add(parsed)
        }

        return hoursList
    }
}
