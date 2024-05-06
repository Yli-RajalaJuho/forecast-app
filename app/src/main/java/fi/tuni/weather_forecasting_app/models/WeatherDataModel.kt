package fi.tuni.weather_forecasting_app.models

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
    val hour: String
)

data class WeatherCode(val code: Int, val conditions: String)
data class WeatherCodeList(
    val weatherCodeList: List<WeatherCode> = listOf(
        WeatherCode(code = 0, conditions = "Clear sky"),
        WeatherCode(code = 1, conditions = "Mainly clear"),
        WeatherCode(code = 2, conditions = "Partly cloudy"),
        WeatherCode(code = 3, conditions = "Overcast"),
        WeatherCode(code = 45, conditions = "Fog"),
        WeatherCode(code = 48, conditions = "Depositing rime fog"),
        WeatherCode(code = 51, conditions = "Drizzle: Light"),
        WeatherCode(code = 53, conditions = "Drizzle: Moderate"),
        WeatherCode(code = 55, conditions = "Drizzle: Dense intensity"),
        WeatherCode(code = 56, conditions = "Freezing Drizzle: Light"),
        WeatherCode(code = 57, conditions = "Freezing Drizzle: Dense intensity"),
        WeatherCode(code = 61, conditions = "Rain: Slight"),
        WeatherCode(code = 63, conditions = "Rain: Moderate"),
        WeatherCode(code = 65, conditions = "Rain: Heavy intensity"),
        WeatherCode(code = 66, conditions = "Freezing Rain: Light"),
        WeatherCode(code = 67, conditions = "Freezing Rain: Heavy intensity"),
        WeatherCode(code = 71, conditions = "Snow fall: Slight"),
        WeatherCode(code = 73, conditions = "Snow fall: Moderate"),
        WeatherCode(code = 75, conditions = "Snow fall: Heavy intensity"),
        WeatherCode(code = 77, conditions = "Snow grains"),
        WeatherCode(code = 80, conditions = "Rain showers: Slight"),
        WeatherCode(code = 81, conditions = "Rain showers: Moderate"),
        WeatherCode(code = 82, conditions = "Rain showers: Violent"),
        WeatherCode(code = 85, conditions = "Snow showers: Slight"),
        WeatherCode(code = 86, conditions = "Snow showers: Heavy"),
        WeatherCode(code = 95, conditions = "Thunderstorm: Slight or moderate"),
        WeatherCode(code = 96, conditions = "Thunderstorm with slight hail"),
        WeatherCode(code = 99, conditions = "Thunderstorm with heavy hail.")
    )
)
