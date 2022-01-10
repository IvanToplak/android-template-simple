package hr.from.ivantoplak.pokemonapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    // Primary brand color
    primary = PokemonRed,
    primaryVariant = PokemonDarkRed,
    onPrimary = White,
    // Secondary brand color
    secondary = PokemonBlue,
    secondaryVariant = PokemonDarkBlue,
    onSecondary = White,
    background = Color.White,
)

private val LightColorPalette = lightColors(
    // Primary brand color
    primary = PokemonRed,
    primaryVariant = PokemonDarkRed,
    onPrimary = White,
    // Secondary brand color
    secondary = PokemonBlue,
    secondaryVariant = PokemonDarkBlue,
    onSecondary = White,
    background = Color.White,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun PokemonAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = PokemonTypography,
        shapes = PokemonShapes,
        content = content
    )
}

val Colors.loadingIndicatorBackground: Color
    @Composable get() = BlackTransparent

val Colors.listDivider: Color
    @Composable get() = PokemonGray
