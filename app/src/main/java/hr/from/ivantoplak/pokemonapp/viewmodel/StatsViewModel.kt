package hr.from.ivantoplak.pokemonapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.coroutines.CoroutineContextProvider
import hr.from.ivantoplak.pokemonapp.mappings.toStatsViewData
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.ui.model.StatViewData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatsViewModel(
    private val pokemonId: Int,
    private val repository: PokemonRepository,
    private val dispatcher: CoroutineContextProvider
) : ViewModel() {

    private val _stats = MutableLiveData<List<StatViewData>>()
    val stats: LiveData<List<StatViewData>> = _stats

    init {
        viewModelScope.launch {
            val flow = withContext(dispatcher.default()) {
                repository.getPokemonStats(pokemonId).map { it.toStatsViewData() }
            }
            flow.catch { ex ->
                Log.e("", "$ex") //log exception TODO
            }.collect { statsList ->
                _stats.value = statsList
            }
        }
    }
}