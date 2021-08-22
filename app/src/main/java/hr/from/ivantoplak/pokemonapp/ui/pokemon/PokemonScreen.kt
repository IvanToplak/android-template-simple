package hr.from.ivantoplak.pokemonapp.ui.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonScreen(
    title: String = stringResource(id = R.string.pokemon_screen_title),
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
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
        )
    }
}

@Composable
fun PokemonScreenContent(
    modifier: Modifier = Modifier,
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
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
        }
    }
}

fun getConstraints(isLandscape: Boolean): ConstraintSet {
    return ConstraintSet {
        val textPokemonName = createRefFor("pokemon_name")
        val imagePokemonSpriteForeground = createRefFor("pokemon_sprite_foreground")
        val imagePokemonSpriteBackground = createRefFor("pokemon_sprite_background")
        val spriteSpacer = createRefFor("sprite_spacer")

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
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonScreenPreview() {
    PokemonAppTheme {
        PokemonScreen("Pokemon", {}, {})
    }
}
