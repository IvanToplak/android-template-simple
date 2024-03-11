package hr.from.ivantoplak.pokemonapp.common.ui.dialog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.common.ui.theme.PokemonAppTheme

@Composable
fun PokemonMessageDialog(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.error_screen_content_title),
    body: String = stringResource(id = R.string.error_screen_content_body),
    onConfirmClicked: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { /* Don't */ },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = body)
        },
        confirmButton = {
            TextButton(onClick = onConfirmClicked) {
                Text(text = stringResource(id = R.string.dialog_ok))
            }
        },
        tonalElevation = 0.0.dp,
    )
}

@Preview
@Composable
private fun PokemonMessageDialogPreview() {
    PokemonAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            PokemonMessageDialog()
        }
    }
}
