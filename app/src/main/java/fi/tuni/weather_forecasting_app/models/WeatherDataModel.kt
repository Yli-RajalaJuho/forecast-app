package fi.tuni.weather_forecasting_app.models

data class WeatherData(
    val hourly: WeatherHourly
)

data class WeatherHourly(
    val time: List<String>,
    val temperature_2m: List<Double>
)

data class SimplifiedWeatherData(
    val date: String,
    val temperature: Double,
    val hour: String
)
