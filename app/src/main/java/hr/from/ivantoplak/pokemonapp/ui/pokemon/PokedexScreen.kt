package hr.from.ivantoplak.pokemonapp.ui.pokemon

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hr.from.ivantoplak.pokemonapp.ui.common.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.ui.common.PokemonWebViewer

enum class PokedexScreenParameter(val param: String) {
    Title("title"),
    WebUrl("webUrl"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    webUrl: String = "",
    onClickBack: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PokemonTopAppBar(
                title = title,
                onClickBack = onClickBack,
            )
        },
    ) { innerPadding ->
        PokemonWebViewer(
            modifier = Modifier.padding(innerPadding),
            webUrl = webUrl,
        )
    }
}
