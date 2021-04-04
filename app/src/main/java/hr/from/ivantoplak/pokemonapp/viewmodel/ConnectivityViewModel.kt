package hr.from.ivantoplak.pokemonapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import hr.from.ivantoplak.pokemonapp.managers.InternetManager
import hr.from.ivantoplak.pokemonapp.viewmodel.event.Event
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map

/**
 * Connectivity view model provides info about connectivity status via observable (starting with API 24).
 */
class ConnectivityViewModel(
    manager: InternetManager
) : ViewModel() {

    @FlowPreview
    val status = manager.observeConnectivityStatus().map { Event(it) }.asLiveData()
}
