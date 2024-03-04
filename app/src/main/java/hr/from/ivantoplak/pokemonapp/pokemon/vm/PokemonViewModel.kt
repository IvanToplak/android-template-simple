package hr.from.ivantoplak.pokemonapp.pokemon.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.pokemon.mappings.toUIPokemon
import hr.from.ivantoplak.pokemonapp.pokemon.model.PokemonRepository
import hr.from.ivantoplak.pokemonapp.pokemon.ui.pokemon.UIPokemon
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val ErrorLoadingPokemons = "Error loading pokemons."

enum class PokemonState {
    Loading,
    ErrorNoData,
    ErrorHasData,
    Success,
}

class PokemonViewModel(
    private val repository: PokemonRepository,
    private val dispatcher: DispatcherProvider,
) : ViewModel() {

    private val _pokemonState = mutableStateOf(PokemonState.Loading)
    val pokemonState: State<PokemonState> get() = _pokemonState

    private val pokemonNames = mutableListOf<String>()

    private val _pokemon = mutableStateOf<UIPokemon?>(null)
    val pokemon: State<UIPokemon?> get() = _pokemon

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
        _pokemonState.value = PokemonState.Loading
        _showErrorMessage.value = false
        try {
            val pokemon = withContext(dispatcher.io()) {
                if (pokemonNames.isEmpty()) pokemonNames.addAll(repository.getPokemonNames())
                repository.getPokemon(pokemonNames.random())?.toUIPokemon()
            }
            when {
                pokemon != null -> {
                    _pokemon.value = pokemon
                    _pokemonState.value = PokemonState.Success
                    _showErrorMessage.value = false
                }
                _pokemon.value != null -> {
                    _pokemonState.value = PokemonState.Success
                    _showErrorMessage.value = false
                }
                // show error message only when there is no API data and no local data
                else -> {
                    _pokemonState.value = PokemonState.ErrorNoData
                    _showErrorMessage.value = true
                }
            }
        } catch (e: Exception) {
            _pokemonState.value =
                if (_pokemon.value != null) PokemonState.ErrorHasData else PokemonState.ErrorNoData
            _showErrorMessage.value = true
            Timber.e(e, ErrorLoadingPokemons)
        }
    }
}
