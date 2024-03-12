package hr.from.ivantoplak.pokemonapp.common.ui.image

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader

/**
 * A component that loads an image from a remote URL.
 */
@Composable
fun PokemonRemoteImage(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    contentDescription: String? = null,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = imageUrl,
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.wrapContentSize(),
            )
        },
        contentDescription = contentDescription,
        imageLoader = LocalContext.current.imageLoader,
    )
}
