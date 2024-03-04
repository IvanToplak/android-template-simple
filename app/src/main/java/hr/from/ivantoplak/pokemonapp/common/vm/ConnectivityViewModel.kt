package hr.from.ivantoplak.pokemonapp.common.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.from.ivantoplak.pokemonapp.common.connectivity.InternetManager
import kotlinx.coroutines.launch

/**
 * Connectivity view model provides info about connectivity status via observable (starting with API 24).
 */
class ConnectivityViewModel(manager: InternetManager) : ViewModel() {

    private val _status = mutableStateOf(InternetManager.ConnectivityStatus.Connected)
    val status: State<InternetManager.ConnectivityStatus> get() = _status

    init {
        viewModelScope.launch {
            manager.observeConnectivityStatus().collect { _status.value = it }
        }
    }
}
