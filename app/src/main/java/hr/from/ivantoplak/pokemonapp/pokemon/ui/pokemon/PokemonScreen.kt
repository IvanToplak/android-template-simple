package hr.from.ivantoplak.pokemonapp.pokemon.ui.pokemon

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActionProvider
import hr.from.ivantoplak.pokemonapp.common.ui.appbar.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.common.ui.dialog.PokemonMessageDialog
import hr.from.ivantoplak.pokemonapp.common.ui.image.PokemonRemoteImage
import hr.from.ivantoplak.pokemonapp.common.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.common.utils.titleCaseFirstChar
import hr.from.ivantoplak.pokemonapp.pokemon.vm.PokemonAction
import hr.from.ivantoplak.pokemonapp.pokemon.vm.PokemonEvent
import hr.from.ivantoplak.pokemonapp.pokemon.vm.PokemonState
import hr.from.ivantoplak.pokemonapp.pokemon.vm.PokemonViewModel
import org.koin.compose.koinInject

@Composable
internal fun PokemonScreen(
    viewModel: PokemonViewModel,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    appNavActionProvider: AppNavActionProvider = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle()

    LaunchedEffect(event) {
        when (val e = event) {
            PokemonEvent.NavigateUp -> {
                appNavActionProvider.appNavActions?.navigateUp()
                viewModel.reduce(PokemonAction.OnEventConsumed)
            }

            is PokemonEvent.NavigateToMoves -> {
                appNavActionProvider.appNavActions?.navigateToMovesScreen(e.pokemon)
                viewModel.reduce(PokemonAction.OnEventConsumed)
            }

            is PokemonEvent.NavigateToStats -> {
                appNavActionProvider.appNavActions?.navigateToStatsScreen(e.pokemon)
                viewModel.reduce(PokemonAction.OnEventConsumed)
            }

            PokemonEvent.NavigateToPokedex -> {
                appNavActionProvider.appNavActions?.navigateToPokedexScreen()
                viewModel.reduce(PokemonAction.OnEventConsumed)
            }

            PokemonEvent.NoEvent -> {}
        }
    }

    PokemonScreenContent(
        modifier = modifier,
        isExpandedScreen = isExpandedScreen,
        state = state,
        reduce = viewModel::reduce,
    )

    if (state.showError) {
        PokemonMessageDialog(
            modifier = modifier,
            onConfirmClicked = { viewModel.reduce(PokemonAction.OnCloseMessageDialog) },
        )
    }
}

@Composable
fun PokemonScreenContent(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean = false,
    state: PokemonState = PokemonState(),
    reduce: (PokemonAction) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PokemonTopAppBar(
                title = stringResource(id = R.string.pokemon_screen_title),
                showBackButton = false,
            )
        },
    ) { innerPadding ->
        PokemonScreenBody(
            modifier = Modifier.padding(innerPadding),
            isExpandedScreen = isExpandedScreen,
            state = state,
            reduce = reduce,
        )
    }
}

@Composable
fun PokemonScreenBody(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean = false,
    state: PokemonState = PokemonState(),
    reduce: (PokemonAction) -> Unit = {},
) {
    val alignment = if (isExpandedScreen) Alignment.Center else Alignment.TopCenter
    ConstraintLayout(
        constraintSet = getConstraints(isExpandedScreen),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .wrapContentSize(align = alignment),
    ) {
        // pokemon name
        val pokemonName = state.pokemon.name.titleCaseFirstChar()

        ClickableText(
            text = AnnotatedString(pokemonName),
            style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            onClick = { reduce(PokemonAction.OnPokedex) },
            modifier = Modifier.layoutId("pokemon_name"),
        )

        // foreground image
        PokemonRemoteImage(
            imageUrl = state.pokemon.frontSpriteUrl,
            contentDescription = stringResource(id = R.string.pokemon_image_front),
            modifier = Modifier
                .layoutId("pokemon_sprite_foreground")
                .size(dimensionResource(id = R.dimen.sprite_image_size))
                .clickable { reduce(PokemonAction.OnPokedex) },
        )

        Spacer(
            modifier = Modifier
                .layoutId("sprite_spacer")
                .width(32.dp),
        )

        // background image
        PokemonRemoteImage(
            imageUrl = state.pokemon.backSpriteUrl,
            contentDescription = stringResource(id = R.string.pokemon_image_back),
            modifier = Modifier
                .layoutId("pokemon_sprite_background")
                .size(dimensionResource(id = R.dimen.sprite_image_size))
                .clickable { reduce(PokemonAction.OnPokedex) },
        )

        val buttonsEnabled = !state.isLoading

        // moves button
        val minButtonHeight = dimensionResource(id = R.dimen.min_button_height)
        val buttonWidth = dimensionResource(id = R.dimen.button_width)
        Button(
            onClick = { reduce(PokemonAction.OnMoves) },
            enabled = buttonsEnabled,
            modifier = Modifier
                .width(buttonWidth)
                .layoutId("pokemon_moves_button"),
        ) {
            Text(
                text = stringResource(id = R.string.moves),
                modifier = Modifier
                    .heightIn(min = minButtonHeight)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        // stats button
        Button(
            onClick = { reduce(PokemonAction.OnStats) },
            enabled = buttonsEnabled,
            modifier = Modifier
                .width(buttonWidth)
                .layoutId("pokemon_stats_button"),
        ) {
            Text(
                text = stringResource(id = R.string.stats),
                modifier = Modifier
                    .heightIn(min = minButtonHeight)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        // refresh button
        Button(
            onClick = { reduce(PokemonAction.OnRefresh) },
            modifier = Modifier
                .layoutId("pokemon_refresh_button")
                .width(buttonWidth),
            enabled = buttonsEnabled,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8F),
                disabledContentColor = contentColorFor(MaterialTheme.colorScheme.primary),
            ),
        ) {
            AnimatedContent(
                targetState = state.isLoading,
                modifier = Modifier
                    .heightIn(min = minButtonHeight)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                contentAlignment = Alignment.Center,
                label = "Refresh Button",
            ) { isLoading ->
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(minButtonHeight),
                        color = MaterialTheme.colorScheme.background,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.refresh),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

private fun getConstraints(isExpandedScreen: Boolean): ConstraintSet {
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

        if (isExpandedScreen) {
            expandedConstraints(refs)
        } else {
            smallAndMediumConstraints(refs)
        }
    }
}

private fun ConstraintSetScope.smallAndMediumConstraints(refs: ComponentRefs) {
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
        bottom.linkTo(parent.bottom, margin = 16.dp)
    }
}

private fun ConstraintSetScope.expandedConstraints(refs: ComponentRefs) {
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

@Preview(showBackground = true)
@Composable
fun PokemonScreenPreviewSmallAndMediumScreen() {
    PokemonAppTheme {
        GetPokemonScreen()
    }
}

@Composable
@Preview(showBackground = true, widthDp = 720, heightDp = 360)
fun PokemonScreenPreviewExpandedScreen() {
    PokemonAppTheme {
        GetPokemonScreen(isExpandedScreen = true)
    }
}

@Composable
private fun GetPokemonScreen(isExpandedScreen: Boolean = false) {
    PokemonScreenContent(
        state = PokemonState(pokemon = UIPokemon(id = 1, "Pikachu")),
        isExpandedScreen = isExpandedScreen,
    )
}
