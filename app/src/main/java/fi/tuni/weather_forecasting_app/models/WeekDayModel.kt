package fi.tuni.weather_forecasting_app.models

data class Day(
    val id: Int,
    val name: String,
    val shortName: String,
    var date: String,
)

data class WeekList(
    val week: List<Day> = listOf(
        Day(id = 0, name = "Monday", shortName = "Mon", date = ""),
        Day(id = 1, name = "Tuesday", shortName = "Tue", date = ""),
        Day(id = 2, name = "Wednesday", shortName = "Wed", date = ""),
        Day(id = 3, name = "Thursday", shortName = "Thu", date = ""),
        Day(id = 4, name = "Friday", shortName = "Fri", date = ""),
        Day(id = 5, name = "Saturday", shortName = "Sat", date = ""),
        Day(id = 6, name = "Sunday", shortName = "Sun", date = "")
    )
)