package hr.from.ivantoplak.pokemonapp.app.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActions
import hr.from.ivantoplak.pokemonapp.app.nav.PokemonNavHost
import hr.from.ivantoplak.pokemonapp.common.ui.theme.PokemonAppTheme

@Composable
fun PokemonContent(widthSizeClass: WindowWidthSizeClass) {
    PokemonAppTheme {
        // navigation
        val navController = rememberNavController()
        val navActions = remember(navController) {
            AppNavActions(navController)
        }

        PokemonNavHost(
            navController = navController,
            navActions = navActions,
            widthSizeClass = widthSizeClass,
        )
    }
}
