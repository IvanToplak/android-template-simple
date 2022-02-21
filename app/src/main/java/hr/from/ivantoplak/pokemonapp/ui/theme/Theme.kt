package hr.from.ivantoplak.pokemonapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColorScheme(
    // primary brand color
    primary = PokemonRed,
    onPrimary = White,
    // secondary brand color
    secondary = PokemonBlue,
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
        colorScheme = colors,
        typography = PokemonTypography,
        // shapes = PokemonShapes,
        content = content,
    )
}

val ColorScheme.loadingIndicatorBackground: Color
    @Composable get() = BlackTransparent

val ColorScheme.listDivider: Color
    @Composable get() = PokemonGray

val ColorScheme.topAppBarContainerColor: Color
    @Composable get() = PokemonRed

val ColorScheme.topAppBarTextColor: Color
    @Composable get() = Color.White
