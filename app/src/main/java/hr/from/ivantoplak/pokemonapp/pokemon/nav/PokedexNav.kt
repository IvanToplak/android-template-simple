package hr.from.ivantoplak.pokemonapp.pokemon.nav

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActionProvider
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavScreen
import hr.from.ivantoplak.pokemonapp.pokemon.ui.pokemon.PokedexScreen
import org.koin.compose.koinInject

private const val PokedexUrl = "https://www.pokemon.com/us/pokedex/"

enum class PokedexScreenParameter(val param: String) {
    Title("title"),
    WebUrl("webUrl"),
}

fun NavGraphBuilder.pokedexScreen() {
    composable(
        route = AppNavScreen.Search.name +
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
        val appNavActionProvider: AppNavActionProvider = koinInject()

        PokedexScreen(
            title = title,
            webUrl = url,
            onClickBack = { appNavActionProvider.appNavActions?.navigateUp() },
        )
    }
}
