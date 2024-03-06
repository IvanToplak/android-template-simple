package hr.from.ivantoplak.pokemonapp.pokemon.vm

import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.common.vm.StateViewModel
import hr.from.ivantoplak.pokemonapp.pokemon.mappings.toUIStats
import hr.from.ivantoplak.pokemonapp.pokemon.model.PokemonRepository
import hr.from.ivantoplak.pokemonapp.pokemon.ui.stats.UIStat
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

private const val ErrorLoadingStats = "Error loading pokemon stats from local store."

sealed interface StatsState {
    data class Ready(val stats: ImmutableList<UIStat>) : StatsState
    data object Error : StatsState
}

sealed interface StatsEvent {
    data object NavigateUp : StatsEvent
    data object NoEvent : StatsEvent
}

sealed interface StatsAction {
    data object OnNavigateUp : StatsAction
    data object OnEventConsumed : StatsAction
}

class StatsViewModel(
    pokemonId: Int,
    repository: PokemonRepository,
    dispatcher: DispatcherProvider,
) : StateViewModel<StatsState, StatsEvent, StatsAction>(
    StatsState.Ready(persistentListOf()),
    StatsEvent.NoEvent,
) {
    init {
        repository.getPokemonStats(pokemonId)
            .map { it.toUIStats() }
            .flowOn(dispatcher.io())
            .onEach { statsList -> StatsState.Ready(stats = statsList).sendToState() }
            .catch { e ->
                Timber.e(e, ErrorLoadingStats)
                StatsState.Error.sendToState()
            }
            .launchIn(viewModelScope)
    }

    override fun reduce(action: StatsAction) {
        when (action) {
            is StatsAction.OnNavigateUp -> StatsEvent.NavigateUp.sendToEvent()
            is StatsAction.OnEventConsumed -> StatsEvent.NoEvent.sendToEvent()
        }
    }
}
