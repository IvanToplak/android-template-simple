package hr.from.ivantoplak.pokemonapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import hr.from.ivantoplak.pokemonapp.ui.navigation.NavActions
import hr.from.ivantoplak.pokemonapp.ui.navigation.PokemonNavHost
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonApp(widthSizeClass: WindowWidthSizeClass) {
    PokemonAppTheme {
        // navigation
        val navController = rememberNavController()
        val navActions = remember(navController) {
            NavActions(navController)
        }

        Scaffold { innerPadding ->
            PokemonNavHost(
                navController = navController,
                navActions = navActions,
                widthSizeClass = widthSizeClass,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
