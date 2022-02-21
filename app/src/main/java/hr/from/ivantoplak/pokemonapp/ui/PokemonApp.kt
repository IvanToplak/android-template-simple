package hr.from.ivantoplak.pokemonapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonApp() {
    PokemonAppTheme {
        val navController = rememberAnimatedNavController()
        Scaffold { innerPadding ->
            PokemonNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
