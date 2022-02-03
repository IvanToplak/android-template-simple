package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val ErrorFetchData = "Error retrieving initial data from the API."

/**
 * Splash screen view model retrieves initial configuration for the app.
 */
class SplashViewModel : ViewModel() {

    private val _isReady = mutableStateOf(false)
    val isReady: State<Boolean> get() = _isReady

    private val _showErrorMessage = mutableStateOf(false)
    val showErrorMessage: State<Boolean> get() = _showErrorMessage

    init {
        viewModelScope.launch {
            try {
                fetch()
                _isReady.value = true
            } catch (e: Exception) {
                Timber.e(e, ErrorFetchData)
                _isReady.value = false
                _showErrorMessage.value = true
            }
        }
    }

    // call suspend fun from the Repository to load initial data (config data)
    private suspend fun fetch(): Unit = withContext(Dispatchers.IO) {
        delay(1000L)
    }
}
