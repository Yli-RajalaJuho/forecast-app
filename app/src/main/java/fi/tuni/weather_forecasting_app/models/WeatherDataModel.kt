package fi.tuni.weather_forecasting_app.models

data class WeatherData(val date: String, val hour: String, val temperature: String)

data class WeatherDataList(
    val data: List<WeatherData> = listOf()
)