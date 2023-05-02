package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.mappings.toUIStats
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.ui.model.UIStat
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

private const val ErrorLoadingStats = "Error loading pokemon stats from local store."

class StatsViewModel(
    pokemonId: Int,
    repository: PokemonRepository,
    dispatcher: DispatcherProvider,
) : ViewModel() {

    private val _stats = mutableStateOf<ImmutableList<UIStat>>(persistentListOf())
    val stats: State<ImmutableList<UIStat>> get() = _stats

    init {
        repository.getPokemonStats(pokemonId)
            .map { it.toUIStats() }
            .flowOn(dispatcher.io())
            .onEach { statsList -> _stats.value = statsList }
            .catch { e -> Timber.e(e, ErrorLoadingStats) }
            .launchIn(viewModelScope)
    }
}
