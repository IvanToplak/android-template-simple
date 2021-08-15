package hr.from.ivantoplak.pokemonapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonApp() {
    PokemonAppTheme {
        val navController = rememberNavController()
        Scaffold { innerPadding ->
            PokemonNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonAppPreview() {
    PokemonApp()
}
