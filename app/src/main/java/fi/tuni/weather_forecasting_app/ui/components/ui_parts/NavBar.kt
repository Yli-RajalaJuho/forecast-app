package fi.tuni.weather_forecasting_app.ui.components.ui_parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavBar(header: String = "", description: String = "") {

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Transparent.compositeOver(
                MaterialTheme.colorScheme.primary)
        ),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu Icon",
                tint = Color.Transparent.compositeOver(MaterialTheme.colorScheme.onPrimary)
            )
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = header,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Transparent.compositeOver(
                        MaterialTheme.colorScheme.onPrimary)
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = description,
                    fontSize = 18.sp,
                    color = Color.Transparent.compositeOver(
                        MaterialTheme.colorScheme.onPrimary)
                )
            }
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings Icon",
                tint = Color.Transparent.compositeOver(MaterialTheme.colorScheme.onPrimary)
            )
        }
    }
}