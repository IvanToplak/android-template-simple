package hr.from.ivantoplak.pokemonapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.theme.BlackTransparent

@Composable
fun PokemonLoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BlackTransparent)
            .clickable { }
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.progress_bar_size))
                .height(dimensionResource(id = R.dimen.progress_bar_size))
                .align(Alignment.Center)
        )
    }
}