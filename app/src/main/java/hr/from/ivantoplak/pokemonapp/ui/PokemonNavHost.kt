package hr.from.ivantoplak.pokemonapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Moves
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Pokemon
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen.Stats
import hr.from.ivantoplak.pokemonapp.ui.moves.MovesScreen
import hr.from.ivantoplak.pokemonapp.ui.pokemon.PokemonScreen
import hr.from.ivantoplak.pokemonapp.ui.stats.StatsScreen

@Composable
fun PokemonNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Pokemon.name,
        modifier = modifier
    ) {
        composable(Pokemon.name) {
            PokemonScreen(
                onClickShowMoves = { navController.navigate(Moves.name) },
                onClickShowStats = { navController.navigate(Stats.name) },
            )
        }
        composable(Moves.name) {
            MovesScreen(
                onClickBack = { navController.navigateUp() }
            )
        }
        composable(Stats.name) {
            StatsScreen(
                onClickBack = { navController.navigateUp() }
            )
        }
    }
}
