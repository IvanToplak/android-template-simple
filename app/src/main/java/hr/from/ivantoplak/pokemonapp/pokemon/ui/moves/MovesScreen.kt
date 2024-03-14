package hr.from.ivantoplak.pokemonapp.pokemon.ui.moves

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActionProvider
import hr.from.ivantoplak.pokemonapp.common.ui.appbar.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.common.ui.error.ErrorScreen
import hr.from.ivantoplak.pokemonapp.common.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.common.ui.theme.listDivider
import hr.from.ivantoplak.pokemonapp.pokemon.vm.MovesAction
import hr.from.ivantoplak.pokemonapp.pokemon.vm.MovesEvent
import hr.from.ivantoplak.pokemonapp.pokemon.vm.MovesState
import hr.from.ivantoplak.pokemonapp.pokemon.vm.MovesViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.koinInject

@Composable
internal fun MovesScreen(
    viewModel: MovesViewModel,
    modifier: Modifier = Modifier,
    appNavActionProvider: AppNavActionProvider = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle()

    LaunchedEffect(event) {
        when (event) {
            MovesEvent.NavigateUp -> {
                appNavActionProvider.appNavActions?.navigateUp()
                viewModel.reduce(MovesAction.OnEventConsumed)
            }

            MovesEvent.NoEvent -> {}
        }
    }

    when (val st = state) {
        is MovesState.Ready -> {
            MovesScreenContent(
                modifier = modifier,
                state = st,
                reduce = viewModel::reduce,
            )
        }

        MovesState.Error -> {
            ErrorScreen(
                modifier = modifier,
                actionButtonText = stringResource(id = R.string.go_back),
                onActionButtonClick = { viewModel.reduce(MovesAction.OnNavigateUp) },
            )
        }
    }
}

@Composable
private fun MovesScreenContent(
    modifier: Modifier = Modifier,
    state: MovesState.Ready = MovesState.Ready(persistentListOf()),
    reduce: (MovesAction) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PokemonTopAppBar(
                title = stringResource(id = R.string.moves_screen_title),
                onClickBack = { reduce(MovesAction.OnNavigateUp) },
            )
        },
    ) { innerPadding ->
        MovesScreenBody(
            modifier = Modifier.padding(innerPadding),
            moves = state.moves,
        )
    }
}

@Composable
private fun MovesScreenBody(
    modifier: Modifier = Modifier,
    moves: ImmutableList<UIMove> = persistentListOf(),
) {
    ConstraintLayout(
        constraintSet = getConstraints(),
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .layoutId("moves_list")
                .fillMaxSize(),
        ) {
            itemsIndexed(moves, key = { _, move -> move.id }) { index, move ->
                MoveRow(
                    modifier = Modifier,
                    move = move,
                )
                if (index < moves.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.listDivider)
                }
            }
        }
    }
}

@Composable
private fun MoveRow(
    modifier: Modifier = Modifier,
    move: UIMove,
) {
    Text(
        text = move.name,
        modifier = modifier.padding(horizontal = 32.dp, vertical = 16.dp),
        style = MaterialTheme.typography.bodyLarge,
    )
}

private fun getConstraints(): ConstraintSet {
    return ConstraintSet {
        val movesList = createRefFor("moves_list")

        constrain(movesList) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}

@Preview(name = "MovesScreenPortrait", showBackground = true)
@Preview(name = "MovesScreenLandscape", showBackground = true, widthDp = 720, heightDp = 360)
@Composable
fun MovesScreenPreview() {
    PokemonAppTheme {
        MovesScreenContent(
            state = MovesState.Ready(
                moves =
                List(15) {
                    UIMove(
                        id = it,
                        name = "roundhouse kick",
                    )
                }.toImmutableList(),
            ),
        )
    }
}
