package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.coroutines.CoroutineContextProvider
import hr.from.ivantoplak.pokemonapp.mappings.toPokemonViewData
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import hr.from.ivantoplak.pokemonapp.ui.model.PokemonViewData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val ERROR_LOADING_POKEMON_API =
    "Error loading pokemons. Please check your internet connection."

private const val ERROR_LOADING_POKEMON_LOCAL =
    "Error loading pokemons from local store."

enum class ViewState {
    LOADING,
    ERROR_NO_DATA,
    ERROR_HAS_DATA,
    SUCCESS,
}

class PokemonViewModel(
    private val repository: PokemonRepository,
    private val dispatcher: CoroutineContextProvider
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private val pokemonNames = mutableListOf<String>()

    private val _pokemon = MutableLiveData<PokemonViewData>()
    val pokemon: LiveData<PokemonViewData?> = _pokemon

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

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
        _viewState.value = ViewState.LOADING
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
                        _viewState.value = ViewState.SUCCESS
                    }
                    _pokemon.value != null -> {
                        _viewState.value = ViewState.SUCCESS
                    }
                    // show error message only when there is no API data and no local data
                    else -> {
                        _viewState.value = ViewState.ERROR_NO_DATA
                        _toastMessage.value = ERROR_LOADING_POKEMON_API
                    }
                }
            }
            onFailure { ex ->
                _viewState.value =
                    if (_pokemon.value != null) ViewState.ERROR_HAS_DATA else ViewState.ERROR_NO_DATA
                _toastMessage.value = ERROR_LOADING_POKEMON_LOCAL
                Timber.e(ex, ERROR_LOADING_POKEMON_LOCAL)
            }
        }
    }

    fun doneShowingToastMessage() {
        _toastMessage.value = ""
    }
}
