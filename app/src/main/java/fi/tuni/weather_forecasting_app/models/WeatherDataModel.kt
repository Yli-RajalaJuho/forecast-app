package fi.tuni.weather_forecasting_app.models

import fi.tuni.weather_forecasting_app.R

data class WeatherData(
    val hourly: WeatherHourly
)

data class WeatherHourly(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val weather_code: List<Int>
)

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
        WeatherCode(code = 0, conditions = "Clear sky", backgroundImage = R.drawable.clear_sky),
        WeatherCode(code = 1, conditions = "Mainly clear", backgroundImage = R.drawable.partly_cloudy),
        WeatherCode(code = 2, conditions = "Partly cloudy", backgroundImage = R.drawable.partly_cloudy),
        WeatherCode(code = 3, conditions = "Overcast", backgroundImage = R.drawable.cloudy),
        WeatherCode(code = 45, conditions = "Fog", backgroundImage = R.drawable.partly_cloudy), // TODO change to fog
        WeatherCode(code = 48, conditions = "Depositing rime fog", backgroundImage = R.drawable.partly_cloudy),
        WeatherCode(code = 51, conditions = "Drizzle: Light", backgroundImage = R.drawable.rain),
        WeatherCode(code = 53, conditions = "Drizzle: Moderate", backgroundImage = R.drawable.rain),
        WeatherCode(code = 55, conditions = "Drizzle: Dense intensity", backgroundImage = R.drawable.rain),
        WeatherCode(code = 56, conditions = "Freezing Drizzle: Light", backgroundImage = R.drawable.cold_rain),
        WeatherCode(code = 57, conditions = "Freezing Drizzle: Dense intensity", backgroundImage = R.drawable.cold_rain),
        WeatherCode(code = 61, conditions = "Rain: Slight", backgroundImage = R.drawable.rain),
        WeatherCode(code = 63, conditions = "Rain: Moderate", backgroundImage = R.drawable.rain),
        WeatherCode(code = 65, conditions = "Rain: Heavy intensity", backgroundImage = R.drawable.rain),
        WeatherCode(code = 66, conditions = "Freezing Rain: Light", backgroundImage = R.drawable.cold_rain),
        WeatherCode(code = 67, conditions = "Freezing Rain: Heavy intensity", backgroundImage = R.drawable.cold_rain),
        WeatherCode(code = 71, conditions = "Snow fall: Slight", backgroundImage = R.drawable.snow),
        WeatherCode(code = 73, conditions = "Snow fall: Moderate", backgroundImage = R.drawable.snow),
        WeatherCode(code = 75, conditions = "Snow fall: Heavy intensity", backgroundImage = R.drawable.snow),
        WeatherCode(code = 77, conditions = "Snow grains", backgroundImage = R.drawable.snow),
        WeatherCode(code = 80, conditions = "Rain showers: Slight", backgroundImage = R.drawable.rain),
        WeatherCode(code = 81, conditions = "Rain showers: Moderate", backgroundImage = R.drawable.rain),
        WeatherCode(code = 82, conditions = "Rain showers: Violent", backgroundImage = R.drawable.rain),
        WeatherCode(code = 85, conditions = "Snow showers: Slight", backgroundImage = R.drawable.snow),
        WeatherCode(code = 86, conditions = "Snow showers: Heavy", backgroundImage = R.drawable.snow),
        WeatherCode(code = 95, conditions = "Thunderstorm: Slight or moderate", backgroundImage = R.drawable.thunder),
        WeatherCode(code = 96, conditions = "Thunderstorm with slight hail", backgroundImage = R.drawable.thunder),
        WeatherCode(code = 99, conditions = "Thunderstorm with heavy hail.", backgroundImage = R.drawable.thunder)
    )
)
