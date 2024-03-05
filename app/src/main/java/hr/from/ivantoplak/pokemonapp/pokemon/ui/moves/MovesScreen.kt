package hr.from.ivantoplak.pokemonapp.pokemon.ui.moves

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActionProvider
import hr.from.ivantoplak.pokemonapp.common.ui.appbar.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.common.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.common.ui.theme.listDivider
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
    MovesScreenContent(
        modifier = modifier,
        moves = viewModel.moves.value,
        onClickBack = { appNavActionProvider.appNavActions?.navigateUp() },
    )
}

@Composable
private fun MovesScreenContent(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.moves_screen_title),
    moves: ImmutableList<UIMove> = persistentListOf(),
    onClickBack: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PokemonTopAppBar(
                title = title,
                onClickBack = onClickBack,
            )
        },
    ) { innerPadding ->
        MovesScreenBody(
            modifier = Modifier.padding(innerPadding),
            moves = moves,
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
                    Divider(color = MaterialTheme.colorScheme.listDivider)
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
            title = "Moves",
            moves = List(15) { UIMove(id = it, name = "roundhouse kick") }.toImmutableList(),
        )
    }
}
