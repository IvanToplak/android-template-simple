package hr.from.ivantoplak.pokemonapp.pokemon.vm

import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.common.vm.StateViewModel
import hr.from.ivantoplak.pokemonapp.pokemon.mappings.toUIPokemon
import hr.from.ivantoplak.pokemonapp.pokemon.model.PokemonRepository
import hr.from.ivantoplak.pokemonapp.pokemon.ui.pokemon.UIPokemon
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val ErrorLoadingPokemons = "Error loading pokemons."

data class PokemonState(
    val pokemon: UIPokemon = UIPokemon(),
    val isLoading: Boolean = false,
    val showError: Boolean = false,
)

sealed interface PokemonEvent {
    data class NavigateToStats(val pokemon: UIPokemon) : PokemonEvent
    data class NavigateToMoves(val pokemon: UIPokemon) : PokemonEvent
    data object NavigateToPokedex : PokemonEvent
    data object NavigateUp : PokemonEvent
    data object NoEvent : PokemonEvent
}

sealed interface PokemonAction {
    data object OnRefresh : PokemonAction
    data object OnStats : PokemonAction
    data object OnMoves : PokemonAction
    data object OnPokedex : PokemonAction
    data object OnCloseMessageDialog : PokemonAction
    data object OnNavigateUp : PokemonAction
    data object OnEventConsumed : PokemonAction
}

class PokemonViewModel(
    private val repository: PokemonRepository,
    private val dispatcher: DispatcherProvider,
) : StateViewModel<PokemonState, PokemonEvent, PokemonAction>(
    PokemonState(),
    PokemonEvent.NoEvent,
) {
    private val pokemonNames = mutableListOf<String>()

    init {
        viewModelScope.launch {
            getRandomPokemon()
        }
    }

    override fun reduce(action: PokemonAction) {
        when (action) {
            PokemonAction.OnRefresh -> onRefresh()
            PokemonAction.OnMoves -> PokemonEvent.NavigateToMoves(state.value.pokemon).sendToEvent()
            PokemonAction.OnStats -> PokemonEvent.NavigateToStats(state.value.pokemon).sendToEvent()
            PokemonAction.OnPokedex -> PokemonEvent.NavigateToPokedex.sendToEvent()
            PokemonAction.OnCloseMessageDialog -> state.value.copy(showError = false).sendToState()
            PokemonAction.OnNavigateUp -> PokemonEvent.NavigateUp.sendToEvent()
            PokemonAction.OnEventConsumed -> PokemonEvent.NoEvent.sendToEvent()
        }
    }

    private fun onRefresh() {
        viewModelScope.launch {
            getRandomPokemon()
        }
    }

    private suspend fun getRandomPokemon() {
        state.value.copy(
            isLoading = true,
            showError = false,
        ).sendToState()
        try {
            val pokemon = withContext(dispatcher.io()) {
                if (pokemonNames.isEmpty()) pokemonNames.addAll(repository.getPokemonNames())
                repository.getPokemon(pokemonNames.random())?.toUIPokemon()
            }
            // Get from API call or local DB, otherwise show error
            if (pokemon != null) {
                state.value.copy(
                    pokemon = pokemon,
                    isLoading = false,
                ).sendToState()
            } else {
                state.value.copy(
                    isLoading = false,
                    showError = true,
                ).sendToState()
            }
        } catch (e: Exception) {
            Timber.e(e, ErrorLoadingPokemons)
            state.value.copy(
                isLoading = false,
                showError = true,
            ).sendToState()
        }
    }
}
