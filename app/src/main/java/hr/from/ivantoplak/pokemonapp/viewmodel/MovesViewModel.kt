package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.mappings.toMovesViewData
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.ui.model.MoveViewData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

private const val ERROR_LOADING_MOVES = "Error loading pokemon moves from local store."

class MovesViewModel(
    private val pokemonId: Int,
    private val repository: PokemonRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _moves = MutableLiveData<List<MoveViewData>>(emptyList())
    val moves: LiveData<List<MoveViewData>> = _moves

    init {
        viewModelScope.launch {
            repository.getPokemonMoves(pokemonId)
                .map { it.toMovesViewData() }
                .flowOn(dispatcher.io())
                .catch { e -> Timber.e(e, ERROR_LOADING_MOVES) }
                .collect { movesList -> _moves.value = movesList }
        }
    }
}
