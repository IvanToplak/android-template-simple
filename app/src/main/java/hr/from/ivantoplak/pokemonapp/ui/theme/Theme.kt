package hr.from.ivantoplak.pokemonapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    // primary brand color
    primary = PokemonRed,
    primaryVariant = PokemonDarkRed,
    onPrimary = White,
    // secondary brand color
    secondary = PokemonBlue,
    secondaryVariant = PokemonDarkBlue,
    onSecondary = White,
    // other colors
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

@Composable
fun PokemonAppTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = PokemonTypography,
        shapes = PokemonShapes,
        content = content,
    )
}

val Colors.loadingIndicatorBackground: Color
    @Composable get() = BlackTransparent

val Colors.listDivider: Color
    @Composable get() = PokemonGray
