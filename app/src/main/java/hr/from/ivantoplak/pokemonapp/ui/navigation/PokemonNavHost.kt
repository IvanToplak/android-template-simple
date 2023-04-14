package hr.from.ivantoplak.pokemonapp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
    navActions: NavActions,
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = AppScreen.Pokemon.name,
        modifier = modifier,
        enterTransition = {
            fadeIn(animationSpec = tween(TransitionDuration))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(TransitionDuration))
        },
    ) {
        // Pokemon screen: /Pokemon
        composable(
            route = AppScreen.Pokemon.name,
        ) {
            PokemonScreen(
                viewModel = koinViewModel(),
                navActions = navActions,
                isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded,
            )
        }

        // Pokemon moves screen: /Moves/{pokemonId}
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

        // Pokemon stats screen: /Stats/{pokemonId}
        composable(
            route = "${AppScreen.Stats.name}/{$NavArgPokemonId}",
            arguments = listOf(navArgument(NavArgPokemonId) { type = NavType.IntType }),
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt(NavArgPokemonId)
            val viewModel = koinViewModel<StatsViewModel> { parametersOf(pokemonId) }

            StatsScreen(
                viewModel = viewModel,
                navActions = navActions,
            )
        }

        // Error screen: /Error?title={title}&body={body}
        composable(
            route = AppScreen.Error.name +
                "?${ErrorScreenParameter.Title.param}={${ErrorScreenParameter.Title.param}}" +
                "&${ErrorScreenParameter.Body.param}={${ErrorScreenParameter.Body.param}}",
            arguments = listOf(
                navArgument(ErrorScreenParameter.Title.param) { nullable = true },
                navArgument(ErrorScreenParameter.Body.param) { nullable = true },
            ),
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString(ErrorScreenParameter.Title.param)
                ?: stringResource(id = R.string.error_screen_content_title)
            val body = backStackEntry.arguments?.getString(ErrorScreenParameter.Body.param)
                ?: stringResource(id = R.string.error_screen_content_body)

            ErrorScreen(
                title = title,
                body = body,
                onClickBack = navActions.navigateUp,
            )
        }

        // Pokemon search (pokedex): /Search?title={title}&webUrl={webUrl}
        composable(
            route = AppScreen.Search.name +
                "?${PokedexScreenParameter.Title.param}={${PokedexScreenParameter.Title.param}}" +
                "&${PokedexScreenParameter.WebUrl.param}={${PokedexScreenParameter.WebUrl.param}}",
            arguments = listOf(
                navArgument(PokedexScreenParameter.Title.param) { nullable = true },
                navArgument(PokedexScreenParameter.WebUrl.param) { nullable = true },
            ),
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString(PokedexScreenParameter.Title.param)
                ?: stringResource(id = R.string.pokedex_screen_title)
            val url = backStackEntry.arguments?.getString(PokedexScreenParameter.WebUrl.param)
                ?: PokedexUrl

            PokedexScreen(
                title = title,
                webUrl = url,
                onClickBack = navActions.navigateUp,
            )
        }
    }
}
