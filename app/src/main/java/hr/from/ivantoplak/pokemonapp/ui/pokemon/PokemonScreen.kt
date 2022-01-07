package hr.from.ivantoplak.pokemonapp.ui.pokemon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ConstraintSetScope
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.extensions.titleCaseFirstChar
import hr.from.ivantoplak.pokemonapp.ui.PokemonAppScreen
import hr.from.ivantoplak.pokemonapp.ui.common.PokemonLoadingIndicator
import hr.from.ivantoplak.pokemonapp.ui.common.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.ui.model.PokemonViewData
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonState
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import org.koin.androidx.compose.get
import java.util.Locale

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel,
    navController: NavHostController
) {
    val pokemon: PokemonViewData? by viewModel.pokemon
    val showErrorMessage: Boolean by viewModel.showErrorMessage

    // show error screen
    if (showErrorMessage) {
        LaunchedEffect(key1 = showErrorMessage) {
            navController.navigate(PokemonAppScreen.Error.name)
            viewModel.onShowErrorMessage()
        }
    }

    PokemonScreenContent(
        pokemon = viewModel.pokemon.value,
        pokemonState = viewModel.pokemonState.value,
        onClickShowMoves = { navController.navigate("${PokemonAppScreen.Moves.name}/${pokemon?.id}") },
        onClickShowStats = { navController.navigate("${PokemonAppScreen.Stats.name}/${pokemon?.id}") },
        onClickRefresh = viewModel::onRefresh,
    )
}

@Composable
fun PokemonScreenContent(
    imageLoader: ImageLoader = get(),
    pokemon: PokemonViewData? = null,
    pokemonState: PokemonState = PokemonState.LOADING,
    title: String = stringResource(id = R.string.pokemon_screen_title),
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
    onClickRefresh: () -> Unit = {},
) {
    Scaffold(topBar = {
        PokemonTopAppBar(
            title = title,
            showBackButton = false
        )
    }) { innerPadding ->
        PokemonScreenBody(
            modifier = Modifier.padding(innerPadding),
            imageLoader = imageLoader,
            pokemon = pokemon,
            pokemonState = pokemonState,
            onClickShowMoves = onClickShowMoves,
            onClickShowStats = onClickShowStats,
            onClickRefresh = onClickRefresh,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalCoilApi::class)
@Composable
fun PokemonScreenBody(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    pokemon: PokemonViewData? = null,
    pokemonState: PokemonState = PokemonState.LOADING,
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
    onClickRefresh: () -> Unit = {},
) {
    BoxWithConstraints(modifier = modifier) {
        val isLandscape = maxWidth > maxHeight
        val constraints = getConstraints(isLandscape)
        ConstraintLayout(
            constraintSet = constraints,
            modifier = modifier.fillMaxSize()
        ) {
            // pokemon name
            Text(
                text = pokemon?.name?.titleCaseFirstChar() ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.layoutId("pokemon_name")
            )

            // foreground image
            Image(
                painter = rememberImagePainter(
                    data = pokemon?.frontSpriteUrl,
                    imageLoader = imageLoader
                ),
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

            // background image
            Image(
                painter = rememberImagePainter(
                    data = pokemon?.backSpriteUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = stringResource(id = R.string.pokemon_image_back),
                modifier = Modifier
                    .layoutId("pokemon_sprite_background")
                    .size(dimensionResource(id = R.dimen.sprite_image_size))
            )

            // moves button
            AnimatedVisibility(
                visible = pokemonState in arrayOf(
                    PokemonState.ERROR_HAS_DATA,
                    PokemonState.SUCCESS
                ),
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.layoutId("pokemon_moves_button")
            ) {
                Button(
                    onClick = { onClickShowMoves() },
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(dimensionResource(id = R.dimen.button_width))
                ) {
                    Text(
                        text = stringResource(id = R.string.moves).uppercase(Locale.getDefault()),
                        style = MaterialTheme.typography.button
                    )
                }
            }

            // stats button
            AnimatedVisibility(
                visible = pokemonState in arrayOf(
                    PokemonState.ERROR_HAS_DATA,
                    PokemonState.SUCCESS
                ),
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.layoutId("pokemon_stats_button")
            ) {
                Button(
                    onClick = { onClickShowStats() },
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(dimensionResource(id = R.dimen.button_width))
                ) {
                    Text(
                        text = stringResource(id = R.string.stats).uppercase(Locale.getDefault()),
                        style = MaterialTheme.typography.button
                    )
                }
            }

            // refresh button
            Button(
                onClick = { onClickRefresh() },
                modifier = Modifier
                    .layoutId("pokemon_refresh_button")
                    .width(dimensionResource(id = R.dimen.button_width))
            ) {
                Text(
                    text = stringResource(id = R.string.refresh).uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.button
                )
            }

            // info message
            AnimatedVisibility(
                visible = pokemonState == PokemonState.ERROR_NO_DATA,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.layoutId("text_info")
            ) {
                Text(
                    text = stringResource(id = R.string.pokemon_info),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }

            // loading indicator
            AnimatedVisibility(
                visible = pokemonState == PokemonState.LOADING,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.layoutId("loading_progress_indicator")
            ) {
                PokemonLoadingIndicator()
            }
        }
    }
}

private fun getConstraints(isLandscape: Boolean): ConstraintSet {
    return ConstraintSet {

        val refs = ComponentRefs(
            textPokemonName = createRefFor("pokemon_name"),
            imagePokemonSpriteForeground = createRefFor("pokemon_sprite_foreground"),
            imagePokemonSpriteBackground = createRefFor("pokemon_sprite_background"),
            spriteSpacer = createRefFor("sprite_spacer"),
            buttonMoves = createRefFor("pokemon_moves_button"),
            buttonStats = createRefFor("pokemon_stats_button"),
            buttonRefresh = createRefFor("pokemon_refresh_button"),
            progressIndicator = createRefFor("loading_progress_indicator"),
            textInfo = createRefFor("text_info")
        )

        if (isLandscape) {
            landscapeConstraints(refs)
        } else {
            portraitConstraints(refs)
        }
    }
}

private fun ConstraintSetScope.portraitConstraints(refs: ComponentRefs) {
    constrain(refs.textPokemonName) {
        top.linkTo(parent.top, margin = 32.dp)
        start.linkTo(parent.start, margin = 16.dp)
        end.linkTo(parent.end, margin = 16.dp)
    }

    createHorizontalChain(
        refs.imagePokemonSpriteForeground,
        refs.spriteSpacer,
        refs.imagePokemonSpriteBackground,
        chainStyle = ChainStyle.Packed(0.5F)
    )

    constrain(refs.imagePokemonSpriteForeground) {
        top.linkTo(refs.textPokemonName.bottom, margin = 32.dp)
        start.linkTo(parent.start, margin = 16.dp)
    }

    constrain(refs.imagePokemonSpriteBackground) {
        bottom.linkTo(refs.imagePokemonSpriteForeground.bottom)
        end.linkTo(parent.end, margin = 16.dp)
    }

    constrain(refs.buttonMoves) {
        top.linkTo(refs.imagePokemonSpriteBackground.bottom, margin = 32.dp)
        end.linkTo(parent.end, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
    }

    constrain(refs.buttonStats) {
        top.linkTo(refs.buttonMoves.bottom, margin = 16.dp)
        end.linkTo(parent.end, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
    }

    constrain(refs.buttonRefresh) {
        height = Dimension.wrapContent
        end.linkTo(parent.end, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
        linkTo(
            top = refs.buttonStats.bottom,
            bottom = parent.bottom,
            topMargin = 16.dp,
            bottomMargin = 32.dp,
            bias = 1F
        )
    }

    constrain(refs.progressIndicator) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
    }

    constrain(refs.textInfo) {
        end.linkTo(parent.end, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
        top.linkTo(parent.top, margin = 16.dp)
        bottom.linkTo(refs.buttonRefresh.top, margin = 16.dp)
    }
}

private fun ConstraintSetScope.landscapeConstraints(refs: ComponentRefs) {
    constrain(refs.textPokemonName) {
        top.linkTo(parent.top, margin = 32.dp)
        start.linkTo(parent.start, margin = 16.dp)
        end.linkTo(parent.end, margin = 16.dp)
    }

    constrain(refs.imagePokemonSpriteForeground) {
        top.linkTo(refs.textPokemonName.bottom, margin = 32.dp)
        start.linkTo(parent.start, margin = 32.dp)
    }

    constrain(refs.imagePokemonSpriteBackground) {
        bottom.linkTo(refs.imagePokemonSpriteForeground.bottom)
        start.linkTo(refs.imagePokemonSpriteForeground.end, margin = 32.dp)
    }

    constrain(refs.buttonMoves) {
        top.linkTo(refs.imagePokemonSpriteBackground.top)
        start.linkTo(refs.buttonStats.start)
    }

    constrain(refs.buttonStats) {
        bottom.linkTo(refs.imagePokemonSpriteBackground.bottom)
        start.linkTo(refs.imagePokemonSpriteBackground.end, margin = 32.dp)
    }

    constrain(refs.buttonRefresh) {
        height = Dimension.fillToConstraints
        top.linkTo(refs.imagePokemonSpriteForeground.top)
        bottom.linkTo(refs.imagePokemonSpriteForeground.bottom)
        end.linkTo(parent.end, margin = 32.dp)
    }

    constrain(refs.progressIndicator) {
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(refs.textInfo) {
        end.linkTo(refs.buttonRefresh.start, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
        top.linkTo(parent.top, margin = 16.dp)
        bottom.linkTo(parent.bottom, margin = 16.dp)
    }
}

private class ComponentRefs(
    val textPokemonName: ConstrainedLayoutReference,
    val imagePokemonSpriteForeground: ConstrainedLayoutReference,
    val imagePokemonSpriteBackground: ConstrainedLayoutReference,
    val spriteSpacer: ConstrainedLayoutReference,
    val buttonMoves: ConstrainedLayoutReference,
    val buttonStats: ConstrainedLayoutReference,
    val buttonRefresh: ConstrainedLayoutReference,
    val progressIndicator: ConstrainedLayoutReference,
    val textInfo: ConstrainedLayoutReference,
)

@Preview(showBackground = true)
@Composable
fun PokemonScreenPreview() {
    PokemonAppTheme {
        GetPokemonScreen()
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360)
@Composable
fun PokemonScreenPreviewLandscape() {
    PokemonAppTheme {
        GetPokemonScreen()
    }
}

@Composable
private fun GetPokemonScreen() {
    PokemonScreenContent(
        imageLoader = LocalImageLoader.current,
        pokemon = PokemonViewData(id = 1, "Pikachu"),
        pokemonState = PokemonState.SUCCESS,
        title = "Pokemon",
        onClickShowStats = {},
        onClickShowMoves = {},
        onClickRefresh = {}
    )
}
