package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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

    // for changing the pager state with a button
    val scrollDirection = remember { mutableStateOf(0) }

    fun getOpacity(): Float {
        var offset = pagerState.currentPageOffsetFraction
        offset = if (offset < 0.0F) 1 - (offset * -2) else 1 - (offset * 2)

        return offset.pow(5)
    }

    LaunchedEffect(scrollDirection.value) {
        if (scrollDirection.value < 0) {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }

        if (scrollDirection.value > 0) {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }

        scrollDirection.value = 0
    }


    HorizontalPager(state = pagerState) { day ->
        Column {

            // header
            Box(
                modifier = Modifier
                    .zIndex(1f)
                    .background(
                        color = Color.Transparent
                            .compositeOver(MaterialTheme.colorScheme.primaryContainer)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        // left arrow
                        if (day != 0) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    scrollDirection.value--
                                },
                                enabled = getOpacity() > 0.5F,
                                colors = ButtonDefaults.buttonColors(
                                    disabledContainerColor = Color.Transparent
                                        .compositeOver(MaterialTheme.colorScheme.primaryContainer),
                                    containerColor = Color.Transparent
                                        .compositeOver(MaterialTheme.colorScheme.primaryContainer),
                                ),
                            ) {
                                Text(
                                    text = "<",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .alpha(getOpacity()),
                                    textAlign = TextAlign.Center,
                                    color = Color.Transparent
                                        .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        // name of the day
                        Text(
                            text = week[day].name,
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.Transparent
                                .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)

                        )
                        // date
                        Text(
                            text = week[day].date,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.Transparent
                                .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        // right arrow
                        if (day != week.size - 1) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    scrollDirection.value++
                                },
                                enabled = getOpacity() > 0.5F,
                                colors = ButtonDefaults.buttonColors(
                                    disabledContainerColor = Color.Transparent
                                        .compositeOver(MaterialTheme.colorScheme.primaryContainer),
                                    containerColor = Color.Transparent
                                        .compositeOver(MaterialTheme.colorScheme.primaryContainer),
                                ),
                            ) {
                                Text(
                                    text = ">",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .alpha(getOpacity()),
                                    textAlign = TextAlign.Center,
                                    color = Color.Transparent
                                        .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                                )
                            }
                        }
                    }
                }
            }

            // ForecastOfTheDay
            Box(modifier = Modifier.zIndex(0f),) {
                ForecastOfTheDay(week[day].date, getOpacity(), weatherDataViewModel)
            }
        }
    }
}
