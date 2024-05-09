package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.navigation.NavController
import kotlin.math.pow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekSelector(navController: NavController) {

    val weeks: List<String> = listOf("Previous", "Current", "Next")

    // current index of the carousel
    val pagerState = rememberPagerState(initialPage = weeks.size/2) { weeks.size }

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

    // Scroll
    HorizontalPager(state = pagerState) {
        Column(
            modifier = Modifier
                .clickable {
                    navController.navigate("weeks-weather-screen/${weeks[pagerState.currentPage]}")
                }
        ) {
            // header
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Transparent
                            .compositeOver(MaterialTheme.colorScheme.primaryContainer)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        // left arrow
                        if (it != 0) {
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
                        // name of the week
                        Text(
                            text = "${weeks[it]} week",
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.Transparent
                                .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)

                        )
                        // description
                        Text(
                            text = "navigate to forecast",
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            color = Color.Transparent.compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        // right arrow
                        if (it != weeks.size - 1) {
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
        }
    }
}