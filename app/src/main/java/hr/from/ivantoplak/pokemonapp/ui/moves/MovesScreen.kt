package hr.from.ivantoplak.pokemonapp.ui.moves

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import hr.from.ivantoplak.pokemonapp.ui.common.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.ui.model.UIMove
import hr.from.ivantoplak.pokemonapp.ui.navigation.NavActions
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.ui.theme.listDivider
import hr.from.ivantoplak.pokemonapp.viewmodel.MovesViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MovesScreen(
    viewModel: MovesViewModel,
    navActions: NavActions,
    modifier: Modifier = Modifier,
) {
    MovesScreenContent(
        modifier = modifier,
        moves = viewModel.moves.value,
        onClickBack = navActions.navigateUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovesScreenContent(
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
fun MovesScreenBody(
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
fun MoveRow(
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
