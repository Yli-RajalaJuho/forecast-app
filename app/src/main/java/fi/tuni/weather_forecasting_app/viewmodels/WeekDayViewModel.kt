package fi.tuni.weather_forecasting_app.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.tuni.weather_forecasting_app.models.Day
import fi.tuni.weather_forecasting_app.models.WeekList
import kotlinx.coroutines.launch

class WeekDayViewModel: ViewModel() {

    private val _currentWeek: SnapshotStateList<Day> = mutableStateListOf<Day>()
    // get current week
    val currentWeek: List<Day> get() = _currentWeek

    init {
        viewModelScope.launch {
            val weekList = WeekList().currentWeek
            _currentWeek.addAll(weekList)
        }
    }
}
