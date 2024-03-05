package hr.from.ivantoplak.pokemonapp.pokemon.nav

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavScreen
import hr.from.ivantoplak.pokemonapp.pokemon.ui.pokemon.PokemonScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.pokemonScreen(
    widthSizeClass: WindowWidthSizeClass,
) {
    composable(
        route = AppNavScreen.Pokemon.name,
    ) {
        PokemonScreen(
            viewModel = koinViewModel(),
            isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded,
        )
    }
}
