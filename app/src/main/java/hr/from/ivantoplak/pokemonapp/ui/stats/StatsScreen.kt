package hr.from.ivantoplak.pokemonapp.ui.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.common.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.ui.model.StatViewData
import hr.from.ivantoplak.pokemonapp.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel

@Composable
fun StatsScreen(
    viewModel: StatsViewModel,
    navController: NavHostController
) {
    StatsScreenContent(
        stats = viewModel.stats.value,
        onClickBack = { navController.navigateUp() }
    )
}

@Composable
fun StatsScreenContent(
    title: String = stringResource(id = R.string.stats_screen_title),
    stats: List<StatViewData> = emptyList(),
    onClickBack: () -> Unit = {},
) {
    Scaffold(topBar = {
        PokemonTopAppBar(
            title = title,
            onClickBack = onClickBack
        )
    }) { innerPadding ->
        StatsScreenBody(
            modifier = Modifier.padding(innerPadding),
            stats = stats
        )
    }
}

@Composable
fun StatsScreenBody(
    modifier: Modifier = Modifier,
    stats: List<StatViewData> = emptyList()
) {
    ConstraintLayout(
        constraintSet = getConstraints(),
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .layoutId("stats_list")
                .fillMaxSize()
        ) {
            items(stats, key = { stat -> stat.id }) { stat ->
                StatsRow(
                    modifier = Modifier,
                    stat = stat
                )
            }
        }
    }
}

@Composable
fun StatsRow(
    modifier: Modifier = Modifier,
    stat: StatViewData
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        // stat name
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1F)
        ) {
            Text(
                text = stat.name,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body1,
                modifier = modifier
                    .padding(vertical = 16.dp)
            )
        }
        // stat value
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = stat.value.toString(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body1,
                modifier = modifier
                    .padding(vertical = 16.dp)
                    .width(dimensionResource(id = R.dimen.stat_value_width))
            )
        }
    }
}

private fun getConstraints(): ConstraintSet {
    return ConstraintSet {
        val statsList = createRefFor("stats_list")

        constrain(statsList) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
    PokemonAppTheme {
        StatsScreenContent(
            title = "Stats",
            stats = List(15) {
                StatViewData(
                    id = it,
                    name = "stat name $it",
                    value = (it + 1) * 100
                )
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360)
@Composable
fun StatsScreenPreviewLandscape() {
    PokemonAppTheme {
        StatsScreenContent(
            title = "Stats",
            stats = List(15) {
                StatViewData(
                    id = it,
                    name = "stat name $it",
                    value = (it + 1) * 100
                )
            }
        )
    }
}
