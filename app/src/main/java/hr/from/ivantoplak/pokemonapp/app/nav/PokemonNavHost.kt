package hr.from.ivantoplak.pokemonapp.app.nav

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import hr.from.ivantoplak.pokemonapp.pokemon.nav.pokedexScreen
import hr.from.ivantoplak.pokemonapp.pokemon.nav.pokemonMovesScreen
import hr.from.ivantoplak.pokemonapp.pokemon.nav.pokemonScreen
import hr.from.ivantoplak.pokemonapp.pokemon.nav.pokemonStatsScreen

@Composable
fun PokemonNavHost(
    navController: NavHostController,
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppNavScreen.Pokemon.name,
        modifier = modifier,
    ) {
        // Pokemon screen: /Pokemon
        pokemonScreen(widthSizeClass = widthSizeClass)

        // Pokemon moves screen: /Moves/{pokemonId}
        pokemonMovesScreen()

        // Pokemon stats screen: /Stats/{pokemonId}
        pokemonStatsScreen()

        // Pokemon search (pokedex): /Search?title={title}&webUrl={webUrl}
        pokedexScreen()
    }
}
