package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fi.tuni.weather_forecasting_app.models.Day
import fi.tuni.weather_forecasting_app.viewmodels.WeatherDataViewModel
import fi.tuni.weather_forecasting_app.viewmodels.WeekDayViewModel
import kotlin.math.pow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ForecastOfTheWeek(
    week: List<Day>,
    weekViewModel: WeekDayViewModel,
    weatherDataViewModel: WeatherDataViewModel
) {

    // current days index for pager states initialization
    val currentDayIndex: Int = weekViewModel.getCurrentDayIndex()

    // current index of the carousel
    val pagerState = rememberPagerState(initialPage = currentDayIndex) { week.size }

    fun getOpacity(): Float {
        var offset = pagerState.currentPageOffsetFraction
        offset = if (offset < 0.0F) 1 - (offset * -2) else 1 - (offset * 2)

        return offset.pow(5)
    }

    HorizontalPager(state = pagerState) { day ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            //verticalArrangement = Arrangement.Top,
            // horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // header
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // date
                Text(
                    text = week[day].date,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    textAlign = TextAlign.Center,
                )

                // left arrow
                if (day != 0) {
                    Text(
                        text = "<",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp)
                            .alpha(getOpacity()),
                        textAlign = TextAlign.Start,
                    )
                }

                // right arrow
                if (day != 6) {
                    Text(
                        text = ">",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp)
                            .alpha(getOpacity()),
                        textAlign = TextAlign.End,
                    )
                }

                // name of the day
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = week[day].name
                )
            }
            Divider()

            CurrentDayForecast(week[day].date, weatherDataViewModel)

        }
    }
}
