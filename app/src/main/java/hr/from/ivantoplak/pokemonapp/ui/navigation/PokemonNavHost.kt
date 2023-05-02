package hr.from.ivantoplak.pokemonapp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost

private const val TransitionDuration = 1000

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
        pokemonScreen(
            navActions = navActions,
            widthSizeClass = widthSizeClass,
        )

        // Pokemon moves screen: /Moves/{pokemonId}
        pokemonMovesScreen(
            navActions = navActions,
        )

        // Pokemon stats screen: /Stats/{pokemonId}
        pokemonStatsScreen(
            navActions = navActions,
        )

        // Error screen: /Error?title={title}&body={body}
        errorScreen(
            navActions = navActions,
        )

        // Pokemon search (pokedex): /Search?title={title}&webUrl={webUrl}
        pokedexScreen(
            navActions = navActions,
        )
    }
}
