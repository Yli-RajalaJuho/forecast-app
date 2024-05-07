package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuni.weather_forecasting_app.models.Day
import fi.tuni.weather_forecasting_app.ui.theme.IndigoGradientBackground
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
    IndigoGradientBackground {
        HorizontalPager(state = pagerState) { day ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                //verticalArrangement = Arrangement.Top,
                // horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // header
                Box(
                    modifier = Modifier
                        .background(color = Color.Transparent
                            .compositeOver(MaterialTheme.colorScheme.primaryContainer)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            // left arrow
                            if (day != 0) {
                                Text(
                                    text = "<",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .alpha(getOpacity())
                                        .padding(start = 30.dp),
                                    textAlign = TextAlign.Start,
                                    color = Color.Transparent
                                        .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 15.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            // date
                            Text(
                                text = week[day].date,
                                fontSize = 12.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color.Transparent
                                    .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                            // name of the day
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                text = week[day].name,
                                color = Color.Transparent
                                    .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)

                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            // right arrow
                            if (day != 6) {
                                Text(
                                    text = ">",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .alpha(getOpacity())
                                        .padding(end = 30.dp),
                                    textAlign = TextAlign.End,
                                    color = Color.Transparent
                                        .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                                )
                            }
                        }
                    }
                }

                Divider(color = Color.Transparent.compositeOver(
                    MaterialTheme.colorScheme.onSurface))

                CurrentDayForecast(week[day].date, getOpacity(), weatherDataViewModel)
            }
        }
    }
}
