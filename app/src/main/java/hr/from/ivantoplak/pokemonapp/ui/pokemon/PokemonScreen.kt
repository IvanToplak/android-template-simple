package hr.from.ivantoplak.pokemonapp.ui.pokemon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonScreen(
    title: String = "",
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Row {
                Text(text = "$title Screen", textAlign = TextAlign.Center)
            }
            Row {
                Button(onClick = { onClickShowMoves() }) {
                    Text(stringResource(id = R.string.moves))
                }
            }
            Row {
                Button(onClick = { onClickShowStats() }) {
                    Text(stringResource(id = R.string.stats))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonScreenPreview() {
    PokemonAppTheme {
        PokemonScreen("Pokemon", {}, {})
    }
}
