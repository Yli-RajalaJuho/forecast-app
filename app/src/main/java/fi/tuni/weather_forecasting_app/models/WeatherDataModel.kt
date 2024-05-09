package fi.tuni.weather_forecasting_app.models

import com.google.gson.annotations.SerializedName
import fi.tuni.weather_forecasting_app.R

data class WeatherDataResponse(
    @SerializedName("hourly") val hourly: WeatherHourly,
    @SerializedName("current") val current: WeatherCurrent
)

data class WeatherHourly(
    val time: List<String?>?,
    val temperature_2m: List<Double?>?,
    val weather_code: List<Int?>?
)

data class WeatherCurrent(
    val time: String?,
    val temperature_2m: Double?,
    val weather_code: Int?
)

// Class that is represented to the user and thus needs to hold some values and not null
data class SimplifiedWeatherData(
    val date: String,
    val temperature: Double,
    val weatherConditions: String,
    val backgroundImage: Int,
    val hour: String
)

data class WeatherCode(val code: Int, val conditions: String, val backgroundImage: Int)
data class WeatherCodeList(
    val weatherCodeList: List<WeatherCode> = listOf(
        WeatherCode(code = 0, conditions = "Clear Sky", backgroundImage = R.drawable.clear_sky),
        WeatherCode(code = 1, conditions = "Mainly Clear", backgroundImage = R.drawable.partly_cloudy),
        WeatherCode(code = 2, conditions = "Partly Cloudy", backgroundImage = R.drawable.cloudy),
        WeatherCode(code = 3, conditions = "Overcast", backgroundImage = R.drawable.overcast),
        WeatherCode(code = 45, conditions = "Fog", backgroundImage = R.drawable.fog),
        WeatherCode(code = 48, conditions = "Depositing Rime Fog", backgroundImage = R.drawable.fog),
        WeatherCode(code = 51, conditions = "Light Drizzle", backgroundImage = R.drawable.slight_rain),
        WeatherCode(code = 53, conditions = "Moderate Drizzle", backgroundImage = R.drawable.slight_rain),
        WeatherCode(code = 55, conditions = "Dense Drizzle", backgroundImage = R.drawable.slight_rain),
        WeatherCode(code = 56, conditions = "Freezing Light Drizzle", backgroundImage = R.drawable.cold_rain),
        WeatherCode(code = 57, conditions = "Freezing Heavy Drizzle", backgroundImage = R.drawable.cold_rain),
        WeatherCode(code = 61, conditions = "Slight Rain", backgroundImage = R.drawable.heavy_rain),
        WeatherCode(code = 63, conditions = "Moderate Rain", backgroundImage = R.drawable.heavy_rain),
        WeatherCode(code = 65, conditions = "Heavy Rain", backgroundImage = R.drawable.heavy_rain),
        WeatherCode(code = 66, conditions = "Freezing Light Rain", backgroundImage = R.drawable.cold_rain),
        WeatherCode(code = 67, conditions = "Freezing Heavy Rain", backgroundImage = R.drawable.cold_rain),
        WeatherCode(code = 71, conditions = "Slight Snow Fall", backgroundImage = R.drawable.snow),
        WeatherCode(code = 73, conditions = "Moderate Snow Fall", backgroundImage = R.drawable.snow),
        WeatherCode(code = 75, conditions = "Heavy Snow Fall", backgroundImage = R.drawable.snow),
        WeatherCode(code = 77, conditions = "Snow Grains", backgroundImage = R.drawable.snow),
        WeatherCode(code = 80, conditions = "Slight Rain Showers", backgroundImage = R.drawable.rain),
        WeatherCode(code = 81, conditions = "Moderate Rain Showers", backgroundImage = R.drawable.rain),
        WeatherCode(code = 82, conditions = "Violent Rain Showers", backgroundImage = R.drawable.rain),
        WeatherCode(code = 85, conditions = "Slight Snow Showers", backgroundImage = R.drawable.snow),
        WeatherCode(code = 86, conditions = "Heavy Snow Showers", backgroundImage = R.drawable.snow),
        WeatherCode(code = 95, conditions = "Slight to Moderate Thunderstorm", backgroundImage = R.drawable.thunder),
        WeatherCode(code = 96, conditions = "Thunderstorm With Slight Hail", backgroundImage = R.drawable.thunder),
        WeatherCode(code = 99, conditions = "Thunderstorm With Heavy Hail.", backgroundImage = R.drawable.thunder)
    )
)
