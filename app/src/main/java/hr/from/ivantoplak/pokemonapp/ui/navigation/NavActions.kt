package hr.from.ivantoplak.pokemonapp.ui.navigation

import androidx.navigation.NavHostController
import hr.from.ivantoplak.pokemonapp.ui.model.UIPokemon

enum class AppScreen {
    Pokemon,
    Moves,
    Stats,
    Error,
    Search,
}

/**
 * Models the navigation actions in the app.
 */
class NavActions(navController: NavHostController) {

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }

    val navigateToMovesScreen: (UIPokemon) -> Unit = { pokemon ->
        navController.navigate("${AppScreen.Moves.name}/${pokemon.id}")
    }

    val navigateToStatsScreen: (UIPokemon) -> Unit = { pokemon ->
        navController.navigate("${AppScreen.Stats.name}/${pokemon.id}")
    }

    val navigateToPokedexScreen: () -> Unit = {
        navController.navigate(AppScreen.Search.name)
    }

    val navigateToErrorScreen: () -> Unit = {
        navController.navigate(AppScreen.Error.name) {
            launchSingleTop = true
        }
    }
}
