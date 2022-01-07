package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.mappings.toPokemonViewData
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.ui.model.PokemonViewData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val ERROR_LOADING_POKEMONS =
    "Error loading pokemons."

enum class PokemonState {
    LOADING,
    ERROR_NO_DATA,
    ERROR_HAS_DATA,
    SUCCESS,
}

class PokemonViewModel(
    private val repository: PokemonRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _pokemonState = mutableStateOf(PokemonState.LOADING)
    val pokemonState: State<PokemonState> get() = _pokemonState

    private val pokemonNames = mutableListOf<String>()

    private val _pokemon = mutableStateOf<PokemonViewData?>(null)
    val pokemon: State<PokemonViewData?> get() = _pokemon

    private val _showErrorMessage = mutableStateOf(false)
    val showErrorMessage: State<Boolean> get() = _showErrorMessage

    init {
        viewModelScope.launch {
            getRandomPokemon()
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            getRandomPokemon()
        }
    }

    fun onShowErrorMessage() {
        _showErrorMessage.value = false
    }

    private suspend fun getRandomPokemon() {
        _pokemonState.value = PokemonState.LOADING
        _showErrorMessage.value = false
        runCatching {
            withContext(dispatcher.io()) {
                if (pokemonNames.isEmpty()) pokemonNames.addAll(repository.getPokemonNames())
                repository.getPokemon(pokemonNames.random())?.toPokemonViewData()
            }
        }.apply {
            onSuccess { pokemon ->
                when {
                    pokemon != null -> {
                        _pokemon.value = pokemon
                        _pokemonState.value = PokemonState.SUCCESS
                        _showErrorMessage.value = false
                    }
                    _pokemon.value != null -> {
                        _pokemonState.value = PokemonState.SUCCESS
                        _showErrorMessage.value = false
                    }
                    // show error message only when there is no API data and no local data
                    else -> {
                        _pokemonState.value = PokemonState.ERROR_NO_DATA
                        _showErrorMessage.value = true
                    }
                }
            }
            onFailure { ex ->
                _pokemonState.value =
                    if (_pokemon.value != null) PokemonState.ERROR_HAS_DATA else PokemonState.ERROR_NO_DATA
                _showErrorMessage.value = true
                Timber.e(ex, ERROR_LOADING_POKEMONS)
            }
        }
    }
}
