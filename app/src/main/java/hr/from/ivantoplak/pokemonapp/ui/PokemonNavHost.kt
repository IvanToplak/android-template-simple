package hr.from.ivantoplak.pokemonapp.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val TransitionDuration = 1000
private const val PokedexUrl = "https://www.pokemon.com/us/pokedex/"
private const val NavArgPokemonId = "pokemonId"

@Composable
fun PokemonNavHost(
    navController: NavHostController,
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Pokemon.name,
        modifier = modifier,
    ) {
        // pokemon screen: /Pokemon
        composable(
            route = Pokemon.name,
            enterTransition = {
                fadeIn(animationSpec = tween(TransitionDuration))
            },
        ) {
            val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

            PokemonScreen(
                viewModel = koinViewModel(),
                navController = navController,
                isExpandedScreen = isExpandedScreen,
            )
        }

        // pokemon moves screen: /Moves/{pokemonId}
        composable(
            route = "${Moves.name}/{$NavArgPokemonId}",
            arguments = listOf(navArgument(NavArgPokemonId) { type = NavType.IntType }),
            enterTransition = {
                fadeIn(animationSpec = tween(TransitionDuration))
            },
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt(NavArgPokemonId)
            val viewModel = koinViewModel<MovesViewModel> { parametersOf(pokemonId) }

            MovesScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        // pokemon stats screen: /Stats/{pokemonId}
        composable(
            route = "${Stats.name}/{$NavArgPokemonId}",
            arguments = listOf(navArgument(NavArgPokemonId) { type = NavType.IntType }),
            enterTransition = {
                fadeIn(animationSpec = tween(TransitionDuration))
            },
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt(NavArgPokemonId)
            val viewModel = koinViewModel<StatsViewModel> { parametersOf(pokemonId) }

            StatsScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        // error screen: /Error?title={title}&body={body}
        composable(
            route = PokemonAppScreen.Error.name +
                "?${ErrorScreenParameter.Title.param}={${ErrorScreenParameter.Title.param}}" +
                "&${ErrorScreenParameter.Body.param}={${ErrorScreenParameter.Body.param}}",
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

        // pokemon search (pokedex): /Search?title={title}&webUrl={webUrl}
        composable(
            route = PokemonAppScreen.Search.name +
                "?${PokedexScreenParameter.Title.param}={${PokedexScreenParameter.Title.param}}" +
                "&${PokedexScreenParameter.WebUrl.param}={${PokedexScreenParameter.WebUrl.param}}",
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
