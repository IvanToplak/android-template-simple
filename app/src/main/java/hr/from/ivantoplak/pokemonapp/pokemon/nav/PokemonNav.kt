package hr.from.ivantoplak.pokemonapp.pokemon.nav

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActions
import hr.from.ivantoplak.pokemonapp.app.nav.AppScreen
import hr.from.ivantoplak.pokemonapp.pokemon.ui.pokemon.PokemonScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.pokemonScreen(
    navActions: AppNavActions,
    widthSizeClass: WindowWidthSizeClass,
) {
    composable(
        route = AppScreen.Pokemon.name,
    ) {
        PokemonScreen(
            viewModel = koinViewModel(),
            navActions = navActions,
            isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded,
        )
    }
}
