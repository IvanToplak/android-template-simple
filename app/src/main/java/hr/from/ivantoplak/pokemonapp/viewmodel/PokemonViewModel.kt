package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.lifecycle.*
import hr.from.ivantoplak.pokemonapp.coroutines.CoroutineContextProvider
import hr.from.ivantoplak.pokemonapp.mappings.toPokemonViewData
import hr.from.ivantoplak.pokemonapp.model.Pokemon
import hr.from.ivantoplak.pokemonapp.ui.model.PokemonViewData
import hr.from.ivantoplak.pokemonapp.repository.PokemonRepository
import kotlinx.coroutines.launch

private const val ERROR_LOADING_POKEMON =
    "Error loading pokemon. Please check your internet connection."

enum class ViewState {
    INITIAL_LOADING,
    LOADING,
    EMPTY,
    READY
}

class PokemonViewModel(
    private val repository: PokemonRepository,
    private val dispatcher: CoroutineContextProvider
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private lateinit var pokemonNames: List<String>

    private val currentPokemon = MutableLiveData<Pokemon>()

    val pokemon: LiveData<PokemonViewData?> = Transformations.map(currentPokemon) {
        it?.toPokemonViewData()
    }

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    init {
        _viewState.value = ViewState.INITIAL_LOADING
        viewModelScope.launch {
            runCatching {
                pokemonNames = repository.getPokemonNames()
                val randIndex = (0..pokemonNames.lastIndex).random()
                currentPokemon.postValue(repository.getPokemon(pokemonNames[randIndex]))
            }.apply {
                onSuccess {
                    _viewState.postValue(ViewState.READY)
                }
                onFailure {
                    _viewState.postValue(ViewState.EMPTY)
                    _toastMessage.postValue(ERROR_LOADING_POKEMON)
                }
            }
        }
    }

    fun onRefresh() {
        _viewState.value =
            if (currentPokemon.value != null) ViewState.LOADING else ViewState.INITIAL_LOADING
        viewModelScope.launch(dispatcher.io()) {
            runCatching {
                val randIndex = (0..pokemonNames.lastIndex).random()
                currentPokemon.postValue(repository.getPokemon(pokemonNames[randIndex]))
            }.apply {
                onSuccess {
                    _viewState.postValue(ViewState.READY)
                }
                onFailure {
                    _viewState.postValue(if (currentPokemon.value != null) ViewState.READY else ViewState.EMPTY)
                    _toastMessage.postValue(ERROR_LOADING_POKEMON)
                }
            }
        }
    }

    fun doneShowingToastMessage() {
        _toastMessage.value = ""
    }
}