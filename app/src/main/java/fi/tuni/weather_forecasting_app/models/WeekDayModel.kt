package fi.tuni.weather_forecasting_app.models

data class Day(val id: Int, val finName: String, val engName: String)

data class WeekList(
    val currentWeek: List<Day> = listOf(
        Day(id = 0, finName = "Ma", engName = "Mon"),
        Day(id = 1, finName = "Ti", engName = "Tue"),
        Day(id = 2, finName = "Ke", engName = "Wed"),
        Day(id = 3, finName = "To", engName = "Thu"),
        Day(id = 4, finName = "Pe", engName = "Fri"),
        Day(id = 5, finName = "La", engName = "Sat"),
        Day(id = 6, finName = "Su", engName = "Sun")
    )
)
