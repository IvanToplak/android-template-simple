package hr.from.ivantoplak.pokemonapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Moves
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Pokemon
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Stats
import hr.from.ivantoplak.pokemonapp.ui.error.ErrorScreen
import hr.from.ivantoplak.pokemonapp.ui.error.ErrorScreenParameter
import hr.from.ivantoplak.pokemonapp.ui.model.MoveViewData
import hr.from.ivantoplak.pokemonapp.ui.model.PokemonViewData
import hr.from.ivantoplak.pokemonapp.ui.model.StatViewData
import hr.from.ivantoplak.pokemonapp.ui.moves.MovesScreen
import hr.from.ivantoplak.pokemonapp.ui.pokemon.PokemonScreen
import hr.from.ivantoplak.pokemonapp.ui.stats.StatsScreen
import hr.from.ivantoplak.pokemonapp.viewmodel.MovesViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonState
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PokemonNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Pokemon.name,
        modifier = modifier
    ) {
        // pokemon screen
        composable(Pokemon.name) {
            val viewModel = getViewModel<PokemonViewModel>()
            val pokemon: PokemonViewData? by viewModel.pokemon.observeAsState()
            val pokemonState: PokemonState by viewModel.pokemonState.observeAsState(PokemonState.LOADING)
            val showErrorMessage: Boolean by viewModel.showErrorMessage.observeAsState(false)

            // show error screen
            if (showErrorMessage) {
                LaunchedEffect(key1 = showErrorMessage) {
                    navController.navigate(PokemonAppScreen.Error.name)
                    viewModel.onShowErrorMessage()
                }
            }

            PokemonScreen(
                pokemon = pokemon,
                pokemonState = pokemonState,
                onClickShowMoves = { navController.navigate("${Moves.name}/${pokemon?.id}") },
                onClickShowStats = { navController.navigate("${Stats.name}/${pokemon?.id}") },
                onClickRefresh = viewModel::onRefresh,
            )
        }

        // pokemon moves screen
        composable(
            route = "${Moves.name}/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId")
            val viewModel = getViewModel<MovesViewModel> { parametersOf(pokemonId) }
            val moves: List<MoveViewData> by viewModel.moves.observeAsState(emptyList())

            MovesScreen(
                moves = moves,
                onClickBack = { navController.navigateUp() }
            )
        }

        // pokemon stats screen
        composable(
            route = "${Stats.name}/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId")
            val viewModel = getViewModel<StatsViewModel> { parametersOf(pokemonId) }
            val stats: List<StatViewData> by viewModel.stats.observeAsState(emptyList())

            StatsScreen(
                stats = stats,
                onClickBack = { navController.navigateUp() }
            )
        }

        // error screen
        composable(
            route = "${PokemonAppScreen.Error.name}?${ErrorScreenParameter.Title.param}={title}&${ErrorScreenParameter.Body.param}={body}",
            arguments = listOf(
                navArgument(ErrorScreenParameter.Title.param) { nullable = true },
                navArgument(ErrorScreenParameter.Body.param) { nullable = true },
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString(ErrorScreenParameter.Title.param)
                ?: stringResource(id = R.string.error_screen_content_title)
            val body = backStackEntry.arguments?.getString(ErrorScreenParameter.Body.param)
                ?: stringResource(id = R.string.error_screen_content_body)

            ErrorScreen(
                title = title,
                body = body,
                onClickBack = { navController.navigateUp() }
            )
        }
    }
}
