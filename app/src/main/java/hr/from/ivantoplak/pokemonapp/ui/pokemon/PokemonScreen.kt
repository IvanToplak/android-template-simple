package hr.from.ivantoplak.pokemonapp.ui.pokemon

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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
    navController: NavHostController,
    modifier: Modifier = Modifier,
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
        modifier = modifier,
        pokemon = viewModel.pokemon.value,
        pokemonState = viewModel.pokemonState.value,
        onClickShowMoves = { navController.navigate("${PokemonAppScreen.Moves.name}/${pokemon?.id}") },
        onClickShowStats = { navController.navigate("${PokemonAppScreen.Stats.name}/${pokemon?.id}") },
        onClickShowPokedex = { navController.navigate(PokemonAppScreen.Search.name) },
        onClickRefresh = viewModel::onRefresh,
    )
}

@Composable
fun PokemonScreenContent(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = get(),
    pokemon: PokemonViewData? = null,
    pokemonState: PokemonState = PokemonState.Loading,
    title: String = stringResource(id = R.string.pokemon_screen_title),
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
    onClickShowPokedex: () -> Unit = {},
    onClickRefresh: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PokemonTopAppBar(
                title = title,
                showBackButton = false,
            )
        },
    ) { innerPadding ->
        PokemonScreenBody(
            modifier = Modifier.padding(innerPadding),
            imageLoader = imageLoader,
            pokemon = pokemon,
            pokemonState = pokemonState,
            onClickShowMoves = onClickShowMoves,
            onClickShowStats = onClickShowStats,
            onClickShowPokedex = onClickShowPokedex,
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
    pokemonState: PokemonState = PokemonState.Loading,
    onClickShowMoves: () -> Unit = {},
    onClickShowStats: () -> Unit = {},
    onClickShowPokedex: () -> Unit = {},
    onClickRefresh: () -> Unit = {},
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isLandscape = maxWidth > maxHeight
        val constraints = getConstraints(isLandscape)
        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier.align(alignment = if (isLandscape) Alignment.Center else Alignment.TopCenter),
        ) {
            // pokemon name
            val pokemonName by remember(pokemon) {
                derivedStateOf { pokemon?.name?.titleCaseFirstChar() ?: "" }
            }
            val isError = pokemonState == PokemonState.ErrorNoData
            val screenTitle =
                if (isError) stringResource(id = R.string.pokemon_info) else pokemonName

            ClickableText(
                text = AnnotatedString(screenTitle),
                style = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
                onClick = { if (!isError) onClickShowPokedex() },
                modifier = Modifier.layoutId("pokemon_name"),
            )

            // foreground image
            Image(
                painter = rememberImagePainter(
                    data = pokemon?.frontSpriteUrl,
                    imageLoader = imageLoader,
                ),
                contentDescription = stringResource(id = R.string.pokemon_image_front),
                modifier = Modifier
                    .layoutId("pokemon_sprite_foreground")
                    .size(dimensionResource(id = R.dimen.sprite_image_size)),
            )

            Spacer(
                modifier = Modifier
                    .layoutId("sprite_spacer")
                    .width(32.dp),
            )

            // background image
            Image(
                painter = rememberImagePainter(
                    data = pokemon?.backSpriteUrl,
                    imageLoader = imageLoader,
                ),
                contentDescription = stringResource(id = R.string.pokemon_image_back),
                modifier = Modifier
                    .layoutId("pokemon_sprite_background")
                    .size(dimensionResource(id = R.dimen.sprite_image_size)),
            )

            val buttonsEnabled by remember(pokemonState) {
                derivedStateOf {
                    pokemonState in arrayOf(PokemonState.ErrorHasData, PokemonState.Success)
                }
            }

            // moves button
            val minButtonHeight = dimensionResource(id = R.dimen.min_button_height)
            val buttonWidth = dimensionResource(id = R.dimen.button_width)
            Button(
                onClick = { onClickShowMoves() },
                enabled = buttonsEnabled,
                modifier = Modifier
                    .width(buttonWidth)
                    .layoutId("pokemon_moves_button"),
            ) {
                Text(
                    text = stringResource(id = R.string.moves).uppercase(Locale.getDefault()),
                    modifier = Modifier
                        .heightIn(min = minButtonHeight)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    style = MaterialTheme.typography.button,
                )
            }

            // stats button
            Button(
                onClick = { onClickShowStats() },
                enabled = buttonsEnabled,
                modifier = Modifier
                    .width(buttonWidth)
                    .layoutId("pokemon_stats_button"),
            ) {
                Text(
                    text = stringResource(id = R.string.stats).uppercase(Locale.getDefault()),
                    modifier = Modifier
                        .heightIn(min = minButtonHeight)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    style = MaterialTheme.typography.button,
                )
            }

            // refresh button
            Button(
                onClick = { onClickRefresh() },
                modifier = Modifier
                    .layoutId("pokemon_refresh_button")
                    .width(buttonWidth),
                enabled = pokemonState != PokemonState.Loading,
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.8F),
                    disabledContentColor = contentColorFor(MaterialTheme.colors.primary),
                )
            ) {
                AnimatedContent(
                    targetState = pokemonState,
                    modifier = Modifier
                        .heightIn(min = minButtonHeight)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    contentAlignment = Alignment.Center,
                ) { state ->
                    if (state == PokemonState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(minButtonHeight),
                            color = MaterialTheme.colors.background,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.refresh).uppercase(Locale.getDefault()),
                            style = MaterialTheme.typography.button,
                        )
                    }
                }
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
        top.linkTo(parent.top, margin = 48.dp)
        start.linkTo(parent.start, margin = 16.dp)
        end.linkTo(parent.end, margin = 16.dp)
    }

    createHorizontalChain(
        refs.imagePokemonSpriteForeground,
        refs.spriteSpacer,
        refs.imagePokemonSpriteBackground,
        chainStyle = ChainStyle.Packed(0.5F),
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
        top.linkTo(refs.buttonStats.bottom, margin = 16.dp)
    }
}

private fun ConstraintSetScope.landscapeConstraints(refs: ComponentRefs) {
    constrain(refs.textPokemonName) {
        top.linkTo(parent.top, margin = 0.dp)
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
        top.linkTo(refs.buttonMoves.bottom, margin = 16.dp)
        start.linkTo(refs.imagePokemonSpriteBackground.end, margin = 32.dp)
    }

    constrain(refs.buttonRefresh) {
        top.linkTo(refs.buttonMoves.top)
        start.linkTo(refs.buttonMoves.end, margin = 32.dp)
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
)

@Preview(name = "PokemonScreenPortrait", showBackground = true)
@Preview(name = "PokemonScreenLandscape", showBackground = true, widthDp = 720, heightDp = 360)
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
        pokemonState = PokemonState.Success,
        title = "Pokemon",
        onClickShowStats = {},
        onClickShowMoves = {},
        onClickRefresh = {},
    )
}
