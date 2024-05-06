package fi.tuni.weather_forecasting_app.viewmodels

import android.icu.util.Calendar
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.tuni.weather_forecasting_app.models.Day
import fi.tuni.weather_forecasting_app.models.WeekList
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

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
    fun getCurrentDayIndex(): Int {
        val week: List<String> = listOf(
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val todayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        _currentWeek.forEach() {
            if(it.name == week[todayIndex - 1]) {
                return it.id
            }
        }
        return 0
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
