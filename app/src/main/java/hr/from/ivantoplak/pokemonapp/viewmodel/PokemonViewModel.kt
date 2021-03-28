package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.mappings.toPokemonViewData
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.ui.model.PokemonViewData
import hr.from.ivantoplak.pokemonapp.viewmodel.event.Event
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

    private val _pokemonState = MutableLiveData<PokemonState>()
    val pokemonState: LiveData<PokemonState> = _pokemonState

    private val pokemonNames = mutableListOf<String>()

    private val _pokemon = MutableLiveData<PokemonViewData?>(null)
    val pokemon: LiveData<PokemonViewData?> = _pokemon

    private val _showMessage = MutableLiveData<Event<Boolean>>()
    val showMessage: LiveData<Event<Boolean>> = _showMessage

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

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

    private suspend fun getRandomPokemon() {
        _pokemonState.value = PokemonState.LOADING
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
                        _showMessage.value = Event(false)
                    }
                    _pokemon.value != null -> {
                        _pokemonState.value = PokemonState.SUCCESS
                        _showMessage.value = Event(false)
                    }
                    // show error message only when there is no API data and no local data
                    else -> {
                        _pokemonState.value = PokemonState.ERROR_NO_DATA
                        _showMessage.value = Event(true)
                    }
                }
            }
            onFailure { ex ->
                _pokemonState.value =
                    if (_pokemon.value != null) PokemonState.ERROR_HAS_DATA else PokemonState.ERROR_NO_DATA
                _showMessage.value = Event(true)
                Timber.e(ex, ERROR_LOADING_POKEMONS)
            }
        }
    }

    fun showMessage(message: String) {
        _message.value = Event(message)
    }
}
