package com.example.recipecomposeapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val RecipesAppDarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    onPrimary = TextPrimaryColorDark,
    error = AccentColorDark,
    onError = TextPrimaryColorDark,
    tertiary = AccentBlueDark,
    tertiaryContainer = SliderTrackColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    onSurfaceVariant = TextSecondaryColorDark,
    outline = DividerColorDark,
)

private val RecipesAppLightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = TextPrimaryColor,
    error = AccentColor,
    onError = TextPrimaryColor,
    tertiary = AccentBlue,
    tertiaryContainer = SliderTrackColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = TextSecondaryColor,
    outline = DividerColor,
)

@Composable
fun RecipeComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> RecipesAppDarkColorScheme
        else -> RecipesAppLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}