package hr.from.ivantoplak.pokemonapp.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonTopAppBar(
    title: String = "",
    showBackButton: Boolean = true,
    onClickBack: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title, maxLines = 1) },
        navigationIcon = if (showBackButton) {
            {
                PokemonBackButton(onClickBack = onClickBack)
            }
        } else null
    )
}

@Composable
fun PokemonBackButton(onClickBack: () -> Unit = {}) {
    IconButton(onClick = { onClickBack() }) {
        Icon(Icons.Filled.ArrowBack, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonTopAppBarPreview() {
    PokemonAppTheme {
        PokemonTopAppBar(title = "Placeholder title", showBackButton = true)
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonBackButtonPreview() {
    PokemonAppTheme {
        PokemonBackButton()
    }
}
