package hr.from.ivantoplak.pokemonapp.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Moves
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Pokemon
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Stats
import hr.from.ivantoplak.pokemonapp.ui.error.ErrorScreen
import hr.from.ivantoplak.pokemonapp.ui.error.ErrorScreenParameter
import hr.from.ivantoplak.pokemonapp.ui.moves.MovesScreen
import hr.from.ivantoplak.pokemonapp.ui.pokemon.PokedexScreen
import hr.from.ivantoplak.pokemonapp.ui.pokemon.PokedexScreenParameter
import hr.from.ivantoplak.pokemonapp.ui.pokemon.PokemonScreen
import hr.from.ivantoplak.pokemonapp.ui.stats.StatsScreen
import hr.from.ivantoplak.pokemonapp.viewmodel.MovesViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

private const val TransitionDuration = 1000
private const val PokedexUrl = "https://www.pokemon.com/us/pokedex/"

@Composable
fun PokemonNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Pokemon.name,
        modifier = modifier,
    ) {
        // pokemon screen
        composable(
            route = Pokemon.name,
            enterTransition = {
                fadeIn(animationSpec = tween(TransitionDuration))
            },
        ) {
            val viewModel = getViewModel<PokemonViewModel>()

            PokemonScreen(viewModel, navController)
        }

        // pokemon moves screen
        composable(
            route = "${Moves.name}/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType }),
            enterTransition = {
                fadeIn(animationSpec = tween(TransitionDuration))
            },
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId")
            val viewModel = getViewModel<MovesViewModel> { parametersOf(pokemonId) }

            MovesScreen(viewModel, navController)
        }

        // pokemon stats screen
        composable(
            route = "${Stats.name}/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType }),
            enterTransition = {
                fadeIn(animationSpec = tween(TransitionDuration))
            },
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId")
            val viewModel = getViewModel<StatsViewModel> { parametersOf(pokemonId) }

            StatsScreen(viewModel, navController)
        }

        // error screen
        composable(
            route = "${PokemonAppScreen.Error.name}?${ErrorScreenParameter.Title.param}={title}&${ErrorScreenParameter.Body.param}={body}",
            arguments = listOf(
                navArgument(ErrorScreenParameter.Title.param) { nullable = true },
                navArgument(ErrorScreenParameter.Body.param) { nullable = true },
            ),
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = TransitionDuration))
            },
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString(ErrorScreenParameter.Title.param)
                ?: stringResource(id = R.string.error_screen_content_title)
            val body = backStackEntry.arguments?.getString(ErrorScreenParameter.Body.param)
                ?: stringResource(id = R.string.error_screen_content_body)

            ErrorScreen(
                title = title,
                body = body,
                onClickBack = { navController.navigateUp() },
            )
        }

        // pokemon search (pokedex)
        composable(
            route = "${PokemonAppScreen.Search.name}?${PokedexScreenParameter.Title.param}={title}&${PokedexScreenParameter.WebUrl.param}={webUrl}",
            arguments = listOf(
                navArgument(PokedexScreenParameter.Title.param) { nullable = true },
                navArgument(PokedexScreenParameter.WebUrl.param) { nullable = true },
            ),
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = TransitionDuration))
            },
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString(PokedexScreenParameter.Title.param)
                ?: stringResource(id = R.string.pokedex_screen_title)
            val url = backStackEntry.arguments?.getString(PokedexScreenParameter.WebUrl.param)
                ?: PokedexUrl

            PokedexScreen(
                title = title,
                webUrl = url,
                onClickBack = { navController.navigateUp() },
            )
        }
    }
}
