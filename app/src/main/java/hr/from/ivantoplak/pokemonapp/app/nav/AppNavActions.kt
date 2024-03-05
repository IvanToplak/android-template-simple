package hr.from.ivantoplak.pokemonapp.app.nav

import androidx.navigation.NavHostController
import hr.from.ivantoplak.pokemonapp.pokemon.ui.pokemon.UIPokemon

enum class AppNavScreen {
    Pokemon,
    Moves,
    Stats,
    Search,
}

/**
 * Models the navigation actions in the app.
 */
class AppNavActions(private val navController: NavHostController) {

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToMovesScreen(pokemon: UIPokemon) {
        navController.navigate("${AppNavScreen.Moves.name}/${pokemon.id}")
    }

    fun navigateToStatsScreen(pokemon: UIPokemon) {
        navController.navigate("${AppNavScreen.Stats.name}/${pokemon.id}")
    }

    fun navigateToPokedexScreen() {
        navController.navigate(AppNavScreen.Search.name)
    }
}
