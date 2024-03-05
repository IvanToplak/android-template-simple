package hr.from.ivantoplak.pokemonapp.app.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActionProvider
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActions
import hr.from.ivantoplak.pokemonapp.app.nav.PokemonNavHost
import hr.from.ivantoplak.pokemonapp.common.ui.theme.PokemonAppTheme
import org.koin.compose.koinInject

@Composable
fun PokemonContent(
    widthSizeClass: WindowWidthSizeClass,
    appNavActionProvider: AppNavActionProvider = koinInject(),
) {
    PokemonAppTheme {
        // navigation
        val navController = rememberNavController()
        remember(navController) {
            AppNavActions(navController).also {
                appNavActionProvider.appNavActions = it
            }
        }

        PokemonNavHost(
            navController = navController,
            widthSizeClass = widthSizeClass,
        )
    }
}
