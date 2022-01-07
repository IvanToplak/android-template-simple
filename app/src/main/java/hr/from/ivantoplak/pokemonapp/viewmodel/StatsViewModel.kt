package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.mappings.toStatsViewData
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.ui.model.StatViewData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

private const val ERROR_LOADING_STATS = "Error loading pokemon stats from local store."

class StatsViewModel(
    private val pokemonId: Int,
    private val repository: PokemonRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _stats = mutableStateOf<List<StatViewData>>(emptyList())
    val stats: State<List<StatViewData>> get() = _stats

    init {
        viewModelScope.launch {
            repository.getPokemonStats(pokemonId)
                .map { it.toStatsViewData() }
                .flowOn(dispatcher.io())
                .catch { e -> Timber.e(e, ERROR_LOADING_STATS) }
                .collect { statsList -> _stats.value = statsList }
        }
    }
}
