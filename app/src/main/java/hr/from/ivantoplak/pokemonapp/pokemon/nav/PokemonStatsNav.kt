package hr.from.ivantoplak.pokemonapp.pokemon.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavScreen
import hr.from.ivantoplak.pokemonapp.pokemon.ui.stats.StatsScreen
import hr.from.ivantoplak.pokemonapp.pokemon.vm.StatsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val NavArgPokemonId = "pokemonId"

fun NavGraphBuilder.pokemonStatsScreen() {
    composable(
        route = "${AppNavScreen.Stats.name}/{$NavArgPokemonId}",
        arguments = listOf(navArgument(NavArgPokemonId) { type = NavType.IntType }),
    ) { backStackEntry ->
        val pokemonId = backStackEntry.arguments?.getInt(NavArgPokemonId)
        val viewModel = koinViewModel<StatsViewModel> { parametersOf(pokemonId) }

        StatsScreen(
            viewModel = viewModel,
        )
    }
}
