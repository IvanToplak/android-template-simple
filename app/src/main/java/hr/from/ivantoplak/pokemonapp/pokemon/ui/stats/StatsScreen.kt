package hr.from.ivantoplak.pokemonapp.pokemon.ui.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.app.nav.AppNavActionProvider
import hr.from.ivantoplak.pokemonapp.common.ui.appbar.PokemonTopAppBar
import hr.from.ivantoplak.pokemonapp.common.ui.error.ErrorScreen
import hr.from.ivantoplak.pokemonapp.common.ui.theme.PokemonAppTheme
import hr.from.ivantoplak.pokemonapp.common.ui.theme.listDivider
import hr.from.ivantoplak.pokemonapp.pokemon.vm.StatsAction
import hr.from.ivantoplak.pokemonapp.pokemon.vm.StatsEvent
import hr.from.ivantoplak.pokemonapp.pokemon.vm.StatsState
import hr.from.ivantoplak.pokemonapp.pokemon.vm.StatsViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.koinInject

@Composable
internal fun StatsScreen(
    viewModel: StatsViewModel,
    modifier: Modifier = Modifier,
    appNavActionProvider: AppNavActionProvider = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle()

    LaunchedEffect(event) {
        when (event) {
            StatsEvent.NavigateUp -> {
                appNavActionProvider.appNavActions?.navigateUp()
                viewModel.reduce(StatsAction.OnEventConsumed)
            }

            StatsEvent.NoEvent -> {}
        }
    }

    when (val st = state) {
        is StatsState.Ready -> {
            StatsScreenContent(
                modifier = modifier,
                state = st,
                reduce = viewModel::reduce,
            )
        }

        StatsState.Error -> {
            ErrorScreen(
                modifier = modifier,
                onClickBack = { viewModel.reduce(StatsAction.OnNavigateUp) },
            )
        }
    }
}

@Composable
private fun StatsScreenContent(
    modifier: Modifier = Modifier,
    state: StatsState.Ready = StatsState.Ready(persistentListOf()),
    reduce: (StatsAction) -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PokemonTopAppBar(
                title = stringResource(id = R.string.stats_screen_title),
                onClickBack = { reduce(StatsAction.OnNavigateUp) },
            )
        },
    ) { innerPadding ->
        StatsScreenBody(
            modifier = Modifier.padding(innerPadding),
            stats = state.stats,
        )
    }
}

@Composable
private fun StatsScreenBody(
    modifier: Modifier = Modifier,
    stats: ImmutableList<UIStat> = persistentListOf(),
) {
    ConstraintLayout(
        constraintSet = getConstraints(),
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .layoutId("stats_list")
                .fillMaxSize(),
        ) {
            itemsIndexed(stats, key = { _, stat -> stat.id }) { index, stat ->
                StatsRow(
                    modifier = Modifier,
                    stat = stat,
                )
                if (index < stats.lastIndex) {
                    Divider(color = MaterialTheme.colorScheme.listDivider)
                }
            }
        }
    }
}

@Composable
private fun StatsRow(
    modifier: Modifier = Modifier,
    stat: UIStat,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        // stat name
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1F),
        ) {
            Text(
                text = stat.name,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier
                    .padding(vertical = 16.dp),
            )
        }
        // stat value
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = stat.value.toString(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier
                    .padding(vertical = 16.dp)
                    .width(dimensionResource(id = R.dimen.stat_value_width)),
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

@Preview(name = "StatsScreenPortrait", showBackground = true)
@Preview(name = "StatsScreenLandscape", showBackground = true, widthDp = 720, heightDp = 360)
@Composable
fun StatsScreenPreview() {
    PokemonAppTheme {
        StatsScreenContent(
            state = StatsState.Ready(
                stats = List(15) {
                    UIStat(
                        id = it,
                        name = "stat name $it",
                        value = (it + 1) * 100,
                    )
                }.toImmutableList(),
            ),
        )
    }
}
