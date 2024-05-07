package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekSelector(navController: NavController) {

    val weeks: List<String> = listOf("Previous", "Current", "Next")

    // current index of the carousel
    val pagerState = rememberPagerState(initialPage = weeks.size/2) { weeks.size }

    HorizontalPager(state = pagerState) { week ->
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Color.Transparent.compositeOver(
                        MaterialTheme.colorScheme.primaryContainer)
                    )
                    .clickable {
                    navController.navigate("weeks-weather-screen/${weeks[week]}")
                }
            ) {
                Text(
                    text = "navigate to",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Transparent
                        .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = "${weeks[week]} week",
                    color = Color.Transparent
                        .compositeOver(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }
            Divider(color = Color.Transparent.compositeOver(
                MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}