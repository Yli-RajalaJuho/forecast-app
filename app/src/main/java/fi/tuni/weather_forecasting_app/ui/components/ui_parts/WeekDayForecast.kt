package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel

@Composable
fun WeekDayForecast() {

    // model for week days
    val viewModel : WeekDayViewModel = viewModel()
    val week = viewModel.currentWeek

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(week.size) {
            Box(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
                Text(text = week[it].engName)
            }
            Divider()
        }
    }
}
