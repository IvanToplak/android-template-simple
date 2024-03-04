package hr.from.ivantoplak.pokemonapp.pokemon.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActions
import hr.from.ivantoplak.pokemonapp.app.nav.AppScreen
import hr.from.ivantoplak.pokemonapp.pokemon.ui.moves.MovesScreen
import hr.from.ivantoplak.pokemonapp.pokemon.vm.MovesViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val NavArgPokemonId = "pokemonId"

fun NavGraphBuilder.pokemonMovesScreen(
    navActions: AppNavActions,
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
