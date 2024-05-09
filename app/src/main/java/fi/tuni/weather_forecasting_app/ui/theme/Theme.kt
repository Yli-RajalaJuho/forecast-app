package fi.tuni.weather_forecasting_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    /*
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    */

    // navigation bar
    primaryContainer = Grey10,
    onPrimaryContainer = Grey99,

    // My color scheme
    primary = Grey10,
    onPrimary = Grey99,

    // button
    secondary = Grey99,
    onSecondary = Grey10,

    // text
    onBackground = Grey99,

    // border color
    onSurface = Grey99,
    )

private val LightColorScheme = lightColorScheme(
    /*
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
     */

    // navigation bar
    primaryContainer = Grey10,
    onPrimaryContainer = Grey99,

    // My color scheme
    primary = Grey99,
    onPrimary = Grey10,

    // button
    secondary = Grey10,
    onSecondary = Grey99,
    //tertiary = Grey10,

    // text
    onBackground = Grey10,

    // border color/ Divider
    onSurface = Grey10,


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// for dark theme
private val IndigoGradientDark = Brush.linearGradient(
    colors = listOf(

        //Color(0xFFFF0077),
        //Color(0xFFFF66CC),
        Grey10,
        Purple40,
        //Color(0xFF8000FF),
        Color(0xff4700b3),
        //Color(0xFFFF6666),
        Grey20,
        //Color(0xff290066),
        //Color(0xFF2D3132),
        Grey10,
        ),
    start = Offset(0f, 800f),
    end = Offset(800f, 0f)
)

// for light theme
private val IndigoGradientLight = Brush.linearGradient(
    colors = listOf(
        Grey99,
        Grey90,
        //Color(0xFFFFFFFF),
        Color(0xFFFF6666),
        Color(0xFFFF0077),
        Color(0xFFFF66CC),
        //Color(0xFF8000FF),
        //Color(0xff4700b3),
        //Color(0xff290066),
        //Color(0xFF2D3132),
        Grey99
    ),
    start = Offset(0f, 800f),
    end = Offset(800f, 0f)
)

@Composable
fun Weather_forecasting_appTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    // Don't use dynamic colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }


        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun IndigoGradientBackground(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable ColumnScope.() -> Unit
) {
    if (darkTheme) {
        Column(
            modifier = Modifier.background(IndigoGradientDark),
            content = content
        )
    } else {
        Column(
            modifier = Modifier.background(IndigoGradientLight),
            content = content
        )
    }
}
