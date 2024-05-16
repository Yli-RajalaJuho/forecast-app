package fi.tuni.weather_forecasting_app.viewmodels

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.tuni.weather_forecasting_app.models.Day
import fi.tuni.weather_forecasting_app.models.WeekList
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeekDayViewModel: ViewModel() {

    // previous week with it's dates
    private val _previousWeek: SnapshotStateList<Day> = mutableStateListOf<Day>()
    val previousWeek: List<Day> get() = _previousWeek

    // current week with it's dates
    private val _currentWeek: SnapshotStateList<Day> = mutableStateListOf<Day>()
    val currentWeek: List<Day> get() = _currentWeek

    // Next week with it's dates
    private val _nextWeek: SnapshotStateList<Day> = mutableStateListOf<Day>()
    val nextWeek: List<Day> get() = _nextWeek

    // return the index for current day of the week
    fun getCurrentDayIndex(week: List<Day>): Int? {
        val format: DateFormat = SimpleDateFormat.getDateInstance()
        val calendar: Calendar = Calendar.getInstance()
        val date: String = format.format(calendar.time)

        for (day in week) {
            if (day.date == date) {
                return day.id
            }
        }
        return null
    }

    private fun initializeWeek(startingDayIndex: Int): List<Day> {
        val format: DateFormat = SimpleDateFormat.getDateInstance()
        val calendar: Calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.add(Calendar.DAY_OF_MONTH, startingDayIndex)

        val weekList = WeekList().week

        weekList.forEach() {
            it.date = format.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return weekList
    }

    init {
        viewModelScope.launch {
            _currentWeek.addAll(initializeWeek(0))
            _previousWeek.addAll(initializeWeek(-7))
            _nextWeek.addAll(initializeWeek(7))
        }
    }
}
