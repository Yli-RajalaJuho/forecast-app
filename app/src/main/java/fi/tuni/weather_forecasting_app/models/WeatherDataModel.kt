package fi.tuni.weather_forecasting_app.models

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.gson.annotations.SerializedName
import fi.tuni.weather_forecasting_app.R

data class WeatherDataResponse(
    @SerializedName("hourly") val hourly: WeatherHourly,
    @SerializedName("current") val current: WeatherCurrent
)

// Forecast weather data
data class WeatherHourly(
    val time: List<String?>?,
    val temperature_2m: List<Double?>?,
    val apparent_temperature: List<Double?>?,
    val weather_code: List<Int?>?,
    val cloud_cover: List<Int?>?,
    val visibility: List<Double?>?,
    val wind_speed_10m: List<Double?>?,
    val wind_direction_10m: List<Int?>?,
    val uv_index: List<Double?>?,
)

// Current weather data
data class WeatherCurrent(
    val time: String?,
    val temperature_2m: Double?,
    val apparent_temperature: Double?,
    val weather_code: Int?,
    val cloud_cover: Int?,
    val visibility: Double?,
    val wind_speed_10m: Double?,
    val wind_direction_10m: Int?,
    val uv_index: Double?,
)

// Class that is represented to the user
data class SimplifiedWeatherData(
    val date: String,
    val hour: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val weatherCode: WeatherCode,
    val cloudCover: Int,
    val visibility: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val uvIndex: Double,
)

data class WeatherCode(val code: Int, val conditions: String, val backgroundImage: Int, val weatherIcon: Int)
data class WeatherCodeList(
    val weatherCodeList: List<WeatherCode> = listOf(
        WeatherCode(code = -1, conditions = "No Data", backgroundImage = R.drawable.clear_sky, weatherIcon = R.drawable.outline_wb_sunny),
        WeatherCode(code = 0, conditions = "Clear Sky", backgroundImage = R.drawable.clear_sky, weatherIcon = R.drawable.outline_wb_sunny),
        WeatherCode(code = 1, conditions = "Mainly Clear", backgroundImage = R.drawable.partly_cloudy, weatherIcon = R.drawable.outline_partly_cloudy_day),
        WeatherCode(code = 2, conditions = "Partly Cloudy", backgroundImage = R.drawable.cloudy, weatherIcon = R.drawable.outline_partly_cloudy_day),
        WeatherCode(code = 3, conditions = "Overcast", backgroundImage = R.drawable.overcast, weatherIcon = R.drawable.outline_cloud),
        WeatherCode(code = 45, conditions = "Fog", backgroundImage = R.drawable.fog, weatherIcon = R.drawable.outline_foggy),
        WeatherCode(code = 48, conditions = "Depositing Rime Fog", backgroundImage = R.drawable.fog, weatherIcon = R.drawable.outline_foggy),
        WeatherCode(code = 51, conditions = "Light Drizzle", backgroundImage = R.drawable.slight_rain, weatherIcon = R.drawable.outline_rainy_light),
        WeatherCode(code = 53, conditions = "Moderate Drizzle", backgroundImage = R.drawable.slight_rain, weatherIcon = R.drawable.outline_rainy),
        WeatherCode(code = 55, conditions = "Dense Drizzle", backgroundImage = R.drawable.slight_rain, weatherIcon = R.drawable.outline_rainy_heavy),
        WeatherCode(code = 56, conditions = "Freezing Light Drizzle", backgroundImage = R.drawable.cold_rain, weatherIcon = R.drawable.outline_rainy_snow),
        WeatherCode(code = 57, conditions = "Freezing Heavy Drizzle", backgroundImage = R.drawable.cold_rain, weatherIcon = R.drawable.outline_rainy_snow),
        WeatherCode(code = 61, conditions = "Slight Rain", backgroundImage = R.drawable.heavy_rain, weatherIcon = R.drawable.outline_rainy_light),
        WeatherCode(code = 63, conditions = "Moderate Rain", backgroundImage = R.drawable.heavy_rain, weatherIcon = R.drawable.outline_rainy),
        WeatherCode(code = 65, conditions = "Heavy Rain", backgroundImage = R.drawable.heavy_rain, weatherIcon = R.drawable.outline_rainy_heavy),
        WeatherCode(code = 66, conditions = "Freezing Light Rain", backgroundImage = R.drawable.cold_rain, weatherIcon = R.drawable.outline_rainy_snow),
        WeatherCode(code = 67, conditions = "Freezing Heavy Rain", backgroundImage = R.drawable.cold_rain, weatherIcon = R.drawable.outline_rainy_snow),
        WeatherCode(code = 71, conditions = "Slight Snow Fall", backgroundImage = R.drawable.snow, weatherIcon = R.drawable.outline_weather_snowy),
        WeatherCode(code = 73, conditions = "Moderate Snow Fall", backgroundImage = R.drawable.snow, weatherIcon = R.drawable.outline_snowing),
        WeatherCode(code = 75, conditions = "Heavy Snow Fall", backgroundImage = R.drawable.snow, weatherIcon = R.drawable.outline_snowing_heavy),
        WeatherCode(code = 77, conditions = "Snow Grains", backgroundImage = R.drawable.snow, weatherIcon = R.drawable.outline_grain),
        WeatherCode(code = 80, conditions = "Slight Rain Showers", backgroundImage = R.drawable.rain, weatherIcon = R.drawable.outline_rainy_light),
        WeatherCode(code = 81, conditions = "Moderate Rain Showers", backgroundImage = R.drawable.rain, weatherIcon = R.drawable.outline_rainy),
        WeatherCode(code = 82, conditions = "Violent Rain Showers", backgroundImage = R.drawable.rain, weatherIcon = R.drawable.outline_rainy_heavy),
        WeatherCode(code = 85, conditions = "Slight Snow Showers", backgroundImage = R.drawable.snow, weatherIcon = R.drawable.outline_weather_snowy),
        WeatherCode(code = 86, conditions = "Heavy Snow Showers", backgroundImage = R.drawable.snow, weatherIcon = R.drawable.outline_snowing_heavy),
        WeatherCode(code = 95, conditions = "Slight to Moderate Thunderstorm", backgroundImage = R.drawable.thunder, weatherIcon = R.drawable.outline_thunderstorm_24),
        WeatherCode(code = 96, conditions = "Thunderstorm With Slight Hail", backgroundImage = R.drawable.thunder, weatherIcon = R.drawable.outline_thunderstorm_24),
        WeatherCode(code = 99, conditions = "Thunderstorm With Heavy Hail.", backgroundImage = R.drawable.thunder, weatherIcon = R.drawable.outline_thunderstorm_24)
    )
)
