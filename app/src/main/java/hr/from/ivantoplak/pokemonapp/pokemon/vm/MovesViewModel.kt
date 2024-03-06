package hr.from.ivantoplak.pokemonapp.pokemon.vm

import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.common.vm.StateViewModel
import hr.from.ivantoplak.pokemonapp.pokemon.mappings.toUIMoves
import hr.from.ivantoplak.pokemonapp.pokemon.model.PokemonRepository
import hr.from.ivantoplak.pokemonapp.pokemon.ui.moves.UIMove
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

private const val ErrorLoadingMoves = "Error loading pokemon moves from local store."

sealed interface MovesState {
    data class Ready(val moves: ImmutableList<UIMove>) : MovesState
    data object Error : MovesState
}

sealed interface MovesEvent {
    data object NavigateUp : MovesEvent
    data object NoEvent : MovesEvent
}

sealed interface MovesAction {
    data object OnNavigateUp : MovesAction
    data object OnEventConsumed : MovesAction
}

class MovesViewModel(
    pokemonId: Int,
    repository: PokemonRepository,
    dispatcher: DispatcherProvider,
) : StateViewModel<MovesState, MovesEvent, MovesAction>(
    MovesState.Ready(persistentListOf()),
    MovesEvent.NoEvent,
) {
    init {
        repository.getPokemonMoves(pokemonId)
            .map { it.toUIMoves() }
            .flowOn(dispatcher.io())
            .onEach { movesList -> MovesState.Ready(moves = movesList).sendToState() }
            .catch { e ->
                Timber.e(e, ErrorLoadingMoves)
                MovesState.Error.sendToState()
            }
            .launchIn(viewModelScope)
    }

    override fun reduce(action: MovesAction) {
        when (action) {
            is MovesAction.OnNavigateUp -> MovesEvent.NavigateUp.sendToEvent()
            is MovesAction.OnEventConsumed -> MovesEvent.NoEvent.sendToEvent()
        }
    }
}
