package hr.from.ivantoplak.pokemonapp.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import hr.from.ivantoplak.pokemonapp.ui.pokemon.PokemonScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.pokemonScreen(
    navActions: NavActions,
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
