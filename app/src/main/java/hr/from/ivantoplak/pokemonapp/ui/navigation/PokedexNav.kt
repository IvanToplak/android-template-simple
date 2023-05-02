package hr.from.ivantoplak.pokemonapp.ui.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.pokemon.PokedexScreen

private const val PokedexUrl = "https://www.pokemon.com/us/pokedex/"

enum class PokedexScreenParameter(val param: String) {
    Title("title"),
    WebUrl("webUrl"),
}

fun NavGraphBuilder.pokedexScreen(
    navActions: NavActions,
) {
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
