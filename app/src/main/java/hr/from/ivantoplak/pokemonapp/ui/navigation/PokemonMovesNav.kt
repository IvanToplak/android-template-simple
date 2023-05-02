package hr.from.ivantoplak.pokemonapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import hr.from.ivantoplak.pokemonapp.ui.moves.MovesScreen
import hr.from.ivantoplak.pokemonapp.viewmodel.MovesViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val NavArgPokemonId = "pokemonId"

fun NavGraphBuilder.pokemonMovesScreen(
    navActions: NavActions,
) {
    composable(
        route = "${AppScreen.Moves.name}/{$NavArgPokemonId}",
        arguments = listOf(navArgument(NavArgPokemonId) { type = NavType.IntType }),
    ) { backStackEntry ->
        val pokemonId = backStackEntry.arguments?.getInt(NavArgPokemonId)
        val viewModel = koinViewModel<MovesViewModel> { parametersOf(pokemonId) }

        MovesScreen(
            viewModel = viewModel,
            navActions = navActions,
        )
    }
}
