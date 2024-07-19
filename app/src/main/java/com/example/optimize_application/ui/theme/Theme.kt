package com.example.optimize_application.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

// Define your color schemes here
private val DarkColorScheme = darkColorScheme(
    primary = MyPrimary,
    primaryContainer = Purple700,
    secondary = MySecond,
    background = BackgroundColor,
    surface = Black,
    onPrimary = White,
    onSecondary = Black,
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = MyPrimary,
    primaryContainer = Purple700,
    secondary = MySecond,
    background = White,
    surface = LightGrey,
    onPrimary = Black,
    onSecondary = White,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}