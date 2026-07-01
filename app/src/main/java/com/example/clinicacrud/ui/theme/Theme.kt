package com.example.clinicacrud.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = md_primary,
    primaryContainer = md_primaryContainer,
    onPrimaryContainer = md_onPrimaryContainer,
    secondary = md_secondary,
    background = md_background,
    surface = md_surface,
    surfaceVariant = md_surfaceVariant,
    onSurface = md_onSurface,
    onSurfaceVariant = md_onSurfaceVariant,
    outline = md_outline,
    error = md_danger
)

private val DarkColors = darkColorScheme(
    primary = md_primaryDark,
    primaryContainer = md_primaryContainerDark,
    onPrimaryContainer = md_onPrimaryContainerDark,
    secondary = md_secondaryDark,
    background = md_backgroundDark,
    surface = md_surfaceDark,
    surfaceVariant = md_surfaceVariantDark,
    onSurface = md_onSurfaceDark,
    onSurfaceVariant = md_onSurfaceVariantDark,
    outline = md_outlineDark,
    error = md_dangerDark
)

@Composable
fun ClinicaCrudTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}