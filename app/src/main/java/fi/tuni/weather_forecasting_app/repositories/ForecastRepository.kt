package fi.tuni.weather_forecasting_app.repositories

import android.util.Log
import fi.tuni.weather_forecasting_app.models.SimplifiedWeatherData
import fi.tuni.weather_forecasting_app.models.WeatherCode
import fi.tuni.weather_forecasting_app.models.WeatherCodeList
import fi.tuni.weather_forecasting_app.models.WeatherCurrent
import fi.tuni.weather_forecasting_app.models.WeatherDataResponse
import fi.tuni.weather_forecasting_app.models.WeatherHourly
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.IndexOutOfBoundsException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface WeatherApiService {
    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String,
        @Query("hourly") hourly: String,
        @Query("past_days") past: Int,
        @Query("forecast_days") forecast: Int,
        @Query("temperature_unit") temperatureUnit: String = "celsius",
        @Query("wind_speed_unit") windSpeedUnit: String = "ms",
    ): WeatherDataResponse
}

object ForecastRepository {
    private val initialWeatherCodes = WeatherCodeList().weatherCodeList

    // These are used to parse dates
    private val formatter: DateFormat = SimpleDateFormat.getDateInstance()
    private val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double,
        current: String,
        hourly: String,
        past: Int,
        forecast: Int,
        temperatureUnit: String,
        windSpeedUnit: String,
    ): WeatherDataResponse {

        return service.getWeatherForecast(
            latitude,
            longitude,
            current,
            hourly,
            past,
            forecast,
            temperatureUnit,
            windSpeedUnit
        )
    }

    // Generate simplified data object of current data
    fun generateSimplifiedCurrentData(weatherData: WeatherCurrent?): SimplifiedWeatherData? {

        val timeStamp: String? = weatherData?.time
        val temperature: Double? = weatherData?.temperature_2m
        val apparentTemperature: Double? = weatherData?.apparent_temperature
        val responseWeatherCode: Int? = weatherData?.weather_code
        val windSpeed: Double? = weatherData?.wind_speed_10m
        val windDirection: Int? = weatherData?.wind_direction_10m

        // If there is no data from the api then return null
        if (weatherData != null) {

            var date: String? = null
            var hour: String? = null

            // if timestamp isn't null parse date and hour
            if (timeStamp != null) {
                // parse date from timestamp
                val parsed: Date = parser.parse(timeStamp.substringBefore("T")) ?: Date()
                date = formatter.format(parsed)

                // parse hour from timestamp
                hour = timeStamp.substringAfter("T")
            }

            // Fetch data related to the weather codes for the current time stamp
            var weatherCode: WeatherCode? = null

            // iterate over the apps own weather code model
            for (weatherCodeData in initialWeatherCodes) {

                // Check if the code matches and change the image and description accordingly
                if (responseWeatherCode == weatherCodeData.code) {
                    weatherCode = weatherCodeData
                }
            }

            return SimplifiedWeatherData(
                date = date ?: "No Data",
                hour = hour ?: "No Data",
                temperature = temperature ?: 0.0, // replace null
                apparentTemperature = apparentTemperature ?: 0.0, // replace null
                weatherCode = weatherCode ?: initialWeatherCodes[0], // no data weather code
                windSpeed = windSpeed ?: 0.0, // replace null
                windDirection = windDirection ?: 0 // replace null
            )
        }

        return null
    }

    // Generate simplified data list of hourly data
    fun generateSimplifiedHourlyData(weatherData: WeatherHourly?): List<SimplifiedWeatherData>? {

        val timeStamps: List<String?>? = weatherData?.time
        val temperatures: List<Double?>? = weatherData?.temperature_2m
        val apparentTemperatures: List<Double?>? = weatherData?.apparent_temperature
        val weatherCodes: List<Int?>? = weatherData?.weather_code
        val windSpeeds: List<Double?>? = weatherData?.wind_speed_10m
        val windDirections: List<Int?>? = weatherData?.wind_direction_10m

        // Try catch block to avoid out of bounds exception
        // in case the weather data holds lists that differ in length
        try {

            // If there is no data from the api then return null
            if (weatherData != null) {
                val dates: List<String>? = parseDates(timeStamps)
                val hours: List<String>? = parseHours(timeStamps)

                val resultList = mutableListOf<SimplifiedWeatherData>()

                // Iterate over all time stamps if it isn't null
                if (timeStamps != null) {
                    for (i in timeStamps.indices) {

                        // Fetch data related to the weather codes for the current time stamp
                        var weatherCode: WeatherCode? = null

                        // iterate over the apps own weather code model
                        for (weatherCodeData in initialWeatherCodes) {

                            // Check if the code matches
                            if (weatherCodes?.get(i) == weatherCodeData.code) {
                                weatherCode = weatherCodeData
                            }
                        }

                        // Try to add each new data block to the new list that contains SimplifiedWeatherData
                        resultList.add(
                            SimplifiedWeatherData(
                                date = dates?.get(i) ?: "No Data",
                                hour = hours?.get(i) ?: "No Data",
                                temperature = temperatures?.get(i) ?: 0.0, // replace null
                                apparentTemperature = apparentTemperatures?.get(i) ?: 0.0, // replace null
                                weatherCode = weatherCode ?: initialWeatherCodes[0], // no data weather code object
                                windSpeed = windSpeeds?.get(i) ?: 0.0, // replace null
                                windDirection = windDirections?.get(i) ?: 0, // replace null
                            )
                        )
                    }
                }

                // finally return the simplified weather data list
                return resultList
            }

            return null

        } catch(e: IndexOutOfBoundsException) {
            Log.d("SIMPLIFIED LIST GENERATOR", "Weather Data lists differ in length!!!")
            return null
        }
    }

    // Separate the date from the timestamps and change it into the format used by WeekDayModel
    private fun parseDates(timeStamps: List<String?>?): List<String>? {
        if (timeStamps != null) {
            val dateList = mutableListOf<String>()

            timeStamps.forEach {
                if (it != null) {
                    val date: Date = parser.parse(it.substringBefore("T")) ?: Date()
                    val parsed = formatter.format(date)
                    dateList.add(parsed)
                } else {
                    dateList.add("No Data")
                }

            }

            return dateList
        }

        return null
    }

    // Separate the hour/minutes from the timestamps
    private fun parseHours(timeStamps: List<String?>?): List<String>? {
        if (timeStamps != null) {
            val hoursList = mutableListOf<String>()

            timeStamps.forEach {
                val parsed = it?.substringAfter("T")
                hoursList.add(parsed ?: "No Data")
            }

            return hoursList
        }

        return null
    }
}
