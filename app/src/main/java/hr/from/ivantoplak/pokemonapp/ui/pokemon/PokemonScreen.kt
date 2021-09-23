package hr.from.ivantoplak.pokemonapp.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme
import java.util.Locale

@Composable
fun PokemonScreen(
    title: String = stringResource(id = R.string.pokemon_screen_title),
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
    onClickRefresh: () -> Unit = {},
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = title, maxLines = 1) }
        )
    }) { innerPadding ->
        PokemonScreenContent(
            modifier = Modifier.padding(innerPadding),
            onClickShowMoves = onClickShowMoves,
            onClickShowStats = onClickShowStats,
            onClickRefresh = onClickRefresh,
        )
    }
}

@Composable
fun PokemonScreenContent(
    modifier: Modifier = Modifier,
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
    onClickRefresh: () -> Unit = {},
) {
    BoxWithConstraints(modifier = modifier) {
        val isLandscape = maxWidth > maxHeight
        val constraints = getConstraints(isLandscape)
        ConstraintLayout(
            constraintSet = constraints,
            animateChanges = true,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.default_pokemon_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.layoutId("pokemon_name")
            )

            Image(
                painter = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(id = R.string.pokemon_image_front),
                modifier = Modifier
                    .layoutId("pokemon_sprite_foreground")
                    .size(dimensionResource(id = R.dimen.sprite_image_size))
            )

            Spacer(
                modifier = Modifier
                    .layoutId("sprite_spacer")
                    .width(32.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(id = R.string.pokemon_image_back),
                modifier = Modifier
                    .layoutId("pokemon_sprite_background")
                    .size(dimensionResource(id = R.dimen.sprite_image_size))
            )

            Button(
                onClick = { onClickShowMoves() },
                modifier = Modifier
                    .layoutId("pokemon_moves_button")
                    .wrapContentHeight()
                    .width(dimensionResource(id = R.dimen.button_width))
            ) {
                Text(text = stringResource(id = R.string.moves).uppercase(Locale.getDefault()))
            }

            Button(
                onClick = { onClickShowStats() },
                modifier = Modifier
                    .layoutId("pokemon_stats_button")
                    .wrapContentHeight()
                    .width(dimensionResource(id = R.dimen.button_width))
            ) {
                Text(text = stringResource(id = R.string.stats).uppercase(Locale.getDefault()))
            }

            Button(
                onClick = { onClickRefresh() },
                modifier = Modifier
                    .layoutId("pokemon_refresh_button")
                    .width(dimensionResource(id = R.dimen.button_width))
            ) {
                Text(text = stringResource(id = R.string.refresh).uppercase(Locale.getDefault()))
            }

            CircularProgressIndicator(
                modifier = Modifier
                    .layoutId("loading_progress_indicator")
                    .width(dimensionResource(id = R.dimen.progress_bar_size))
                    .height(dimensionResource(id = R.dimen.progress_bar_size))
                    .alpha(0.6F)
            )
        }
    }
}

private fun getConstraints(isLandscape: Boolean): ConstraintSet {
    return ConstraintSet {
        val textPokemonName = createRefFor("pokemon_name")
        val imagePokemonSpriteForeground = createRefFor("pokemon_sprite_foreground")
        val imagePokemonSpriteBackground = createRefFor("pokemon_sprite_background")
        val spriteSpacer = createRefFor("sprite_spacer")
        val buttonMoves = createRefFor("pokemon_moves_button")
        val buttonStats = createRefFor("pokemon_stats_button")
        val buttonRefresh = createRefFor("pokemon_refresh_button")
        val progressIndicator = createRefFor("loading_progress_indicator")

        if (isLandscape) {
            constrain(textPokemonName) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }

            constrain(imagePokemonSpriteForeground) {
                top.linkTo(textPokemonName.bottom, margin = 32.dp)
                start.linkTo(parent.start, margin = 32.dp)
            }

            constrain(imagePokemonSpriteBackground) {
                bottom.linkTo(imagePokemonSpriteForeground.bottom)
                start.linkTo(imagePokemonSpriteForeground.end, margin = 32.dp)
            }

            constrain(buttonMoves) {
                top.linkTo(imagePokemonSpriteBackground.top, margin = 8.dp)
                start.linkTo(buttonStats.start)
            }

            constrain(buttonStats) {
                top.linkTo(buttonMoves.bottom, margin = 16.dp)
                start.linkTo(imagePokemonSpriteBackground.end, margin = 32.dp)
            }

            constrain(buttonRefresh) {
                height = Dimension.fillToConstraints
                top.linkTo(buttonMoves.top)
                bottom.linkTo(buttonStats.bottom)
                end.linkTo(parent.end, margin = 32.dp)
            }

            constrain(progressIndicator) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(buttonStats.end)
                end.linkTo(buttonRefresh.start)
            }
        } else {
            constrain(textPokemonName) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }

            createHorizontalChain(
                imagePokemonSpriteForeground,
                spriteSpacer,
                imagePokemonSpriteBackground,
                chainStyle = ChainStyle.Packed(0.5F)
            )

            constrain(imagePokemonSpriteForeground) {
                top.linkTo(textPokemonName.bottom, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }

            constrain(imagePokemonSpriteBackground) {
                bottom.linkTo(imagePokemonSpriteForeground.bottom)
                end.linkTo(parent.end, margin = 16.dp)
            }

            constrain(buttonMoves) {
                top.linkTo(imagePokemonSpriteBackground.bottom, margin = 32.dp)
                end.linkTo(parent.end, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }

            constrain(buttonStats) {
                top.linkTo(buttonMoves.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }

            constrain(buttonRefresh) {
                height = Dimension.wrapContent
                end.linkTo(parent.end, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                linkTo(
                    top = buttonStats.bottom,
                    bottom = parent.bottom,
                    topMargin = 16.dp,
                    bottomMargin = 32.dp,
                    bias = 1F
                )
            }

            constrain(progressIndicator) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(buttonStats.bottom)
                bottom.linkTo(buttonRefresh.top)
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

@Preview(showBackground = true, widthDp = 720, heightDp = 360)
@Composable
fun PokemonScreenPreviewLandscape() {
    PokemonAppTheme {
        PokemonScreen("Pokemon", {}, {})
    }
}
