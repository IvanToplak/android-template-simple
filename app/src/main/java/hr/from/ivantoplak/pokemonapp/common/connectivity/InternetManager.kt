package hr.from.ivantoplak.pokemonapp.common.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

/**
 * Internet manager provides info about connectivity status via [isOnline] property
 * or [connectivityStatus] observable (starting with API 24).
 */
class InternetManager(
    private val connectivityManager: ConnectivityManager,
) {
    enum class ConnectivityStatus {
        Connected,
        NotConnected,
    }

    private val connectivityStatus = MutableStateFlow(ConnectivityStatus.Connected)

    init {
        connectivityManager.registerDefaultNetworkCallback(
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    connectivityStatus.tryEmit(ConnectivityStatus.Connected)
                }

                override fun onLost(network: Network) {
                    connectivityStatus.tryEmit(ConnectivityStatus.NotConnected)
                }
            },
        )
    }

    val isOnline: Boolean
        get() {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            return actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }

    @OptIn(FlowPreview::class)
    fun observeConnectivityStatus(): Flow<ConnectivityStatus> =
        connectivityStatus.debounce(500)
}
