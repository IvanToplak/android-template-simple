package hr.from.ivantoplak.pokemonapp.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.ui.theme.topAppBarContainerColor
import hr.from.ivantoplak.pokemonapp.ui.theme.topAppBarTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    showBackButton: Boolean = true,
    onClickBack: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title, maxLines = 1, style = MaterialTheme.typography.titleMedium) },
        modifier = modifier,
        navigationIcon = if (showBackButton) {
            {
                PokemonBackButton(onClickBack = onClickBack)
            }
        } else {
            {}
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarContainerColor,
            navigationIconContentColor = MaterialTheme.colorScheme.topAppBarTextColor,
            titleContentColor = MaterialTheme.colorScheme.topAppBarTextColor,
            actionIconContentColor = MaterialTheme.colorScheme.topAppBarTextColor,
        ),
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
