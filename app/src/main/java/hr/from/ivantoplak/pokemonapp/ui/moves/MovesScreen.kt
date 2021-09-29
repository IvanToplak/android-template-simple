package hr.from.ivantoplak.pokemonapp.ui.moves

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import hr.from.ivantoplak.pokemonapp.ui.model.MoveViewData
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun MovesScreen(
    title: String = stringResource(id = R.string.moves_screen_title),
    moves: List<MoveViewData> = emptyList(),
    onClickBack: () -> Unit = {},
) {
    Scaffold(topBar = {
        PokemonTopAppBar(
            title = title,
            onClickBack = onClickBack
        )
    }) { innerPadding ->
        MovesScreenContent(
            modifier = Modifier.padding(innerPadding),
            moves = moves
        )
    }
}

@Composable
fun MovesScreenContent(
    modifier: Modifier = Modifier,
    moves: List<MoveViewData> = emptyList()
) {
    BoxWithConstraints(modifier = modifier) {
        val constraints = getConstraints()
        ConstraintLayout(
            constraintSet = constraints,
            animateChanges = true,
            modifier = modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .layoutId("moves_list")
                    .fillMaxSize()
            ) {
                items(moves, key = { move -> move.id }) { move ->
                    MoveRow(
                        modifier = Modifier,
                        move = move
                    )
                }
            }
        }
    }
}

@Composable
fun MoveRow(
    modifier: Modifier = Modifier,
    move: MoveViewData
) {
    Text(text = move.name, modifier = modifier.padding(horizontal = 32.dp, vertical = 16.dp))
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

@Preview(showBackground = true)
@Composable
fun MovesScreenPreview() {
    PokemonAppTheme {
        MovesScreen(
            title = "Moves",
            moves = List(15) { MoveViewData(id = it, name = "roundhouse kick") }
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360)
@Composable
fun MovesScreenPreviewLandscape() {
    PokemonAppTheme {
        MovesScreen(
            title = "Moves",
            moves = List(15) { MoveViewData(id = it, name = "roundhouse kick") }
        )
    }
}