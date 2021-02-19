package hr.from.ivantoplak.pokemonapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.coroutines.CoroutineContextProvider
import hr.from.ivantoplak.pokemonapp.mappings.toMovesViewData
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.ui.model.MoveViewData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovesViewModel(
    private val pokemonId: Int,
    private val repository: PokemonRepository,
    private val dispatcher: CoroutineContextProvider
) : ViewModel() {

    private val _moves = MutableLiveData<List<MoveViewData>>()
    val moves: LiveData<List<MoveViewData>> = _moves

    init {
        viewModelScope.launch {
            val flow = withContext(dispatcher.default()) {
                repository.getPokemonMoves(pokemonId).map { it.toMovesViewData() }
            }
            flow.catch { ex ->
                Log.e("", "$ex") //log exception TODO
            }.collect { movesList ->
                _moves.value = movesList
            }
        }
    }
}