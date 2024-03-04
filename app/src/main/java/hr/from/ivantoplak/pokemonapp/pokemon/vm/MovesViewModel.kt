package hr.from.ivantoplak.pokemonapp.pokemon.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
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

class MovesViewModel(
    pokemonId: Int,
    repository: PokemonRepository,
    dispatcher: DispatcherProvider,
) : ViewModel() {

    private val _moves = mutableStateOf<ImmutableList<UIMove>>(persistentListOf())
    val moves: State<ImmutableList<UIMove>> get() = _moves

    init {
        repository.getPokemonMoves(pokemonId)
            .map { it.toUIMoves() }
            .flowOn(dispatcher.io())
            .onEach { movesList -> _moves.value = movesList }
            .catch { e -> Timber.e(e, ErrorLoadingMoves) }
            .launchIn(viewModelScope)
    }
}
