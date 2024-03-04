package hr.from.ivantoplak.pokemonapp.common.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED
import android.bluetooth.BluetoothAdapter.ERROR
import android.bluetooth.BluetoothAdapter.EXTRA_STATE
import android.bluetooth.BluetoothAdapter.STATE_CONNECTED
import android.bluetooth.BluetoothAdapter.STATE_CONNECTING
import android.bluetooth.BluetoothAdapter.STATE_DISCONNECTED
import android.bluetooth.BluetoothAdapter.STATE_DISCONNECTING
import android.bluetooth.BluetoothAdapter.STATE_ON
import android.bluetooth.BluetoothAdapter.STATE_TURNING_ON
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.common.coroutines.broadcastReceiverFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.pow

interface BluetoothManager {

    val isBluetoothEnabled: StateFlow<Boolean>

    fun getBleDevice(macAddress: String): BluetoothDevice
}

@OptIn(FlowPreview::class)
class BluetoothManagerImpl(
    context: Context,
    dispatcherProvider: DispatcherProvider,
    private val manager: BluetoothManager,
) :
    hr.from.ivantoplak.pokemonapp.common.bluetooth.BluetoothManager {

    private val coroutineScope = CoroutineScope(dispatcherProvider.main())

    private val adapter get() = manager.adapter

    private val _isBluetoothEnabled = MutableStateFlow(adapter.isEnabled)
    override val isBluetoothEnabled get() = _isBluetoothEnabled

    init {
        coroutineScope.launch {
            context.broadcastReceiverFlow(IntentFilter(ACTION_STATE_CHANGED))
                .mapLatest { intent -> intent.getIsBluetoothEnabled() }
                .catch { Timber.e(it) }
                .debounce(1000)
                .collect {
                    if (_isBluetoothEnabled.value != it) {
                        _isBluetoothEnabled.value = it
                    }
                }
        }
    }

    override fun getBleDevice(macAddress: String): BluetoothDevice =
        adapter.getRemoteDevice(macAddress)
}

@SuppressLint("MissingPermission")
fun Context.enableBluetooth() {
    startActivity(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
}

private fun Intent.getIsBluetoothEnabled(): Boolean = when (getIntExtra(EXTRA_STATE, ERROR)) {
    STATE_TURNING_ON, STATE_ON, STATE_CONNECTING, STATE_CONNECTED, STATE_DISCONNECTING, STATE_DISCONNECTED -> true
    else -> false // STATE_TURNING_OFF, STATE_OFF
}

/**
 * Exponential backoff using the following formula:
 *
 * ```
 * delay = base * multiplier ^ retry
 * ```
 *
 * For example (using `base = 100` and `multiplier = 2`):
 *
 * | retry | delay |
 * |-------|-------|
 * |   1   |   100 |
 * |   2   |   200 |
 * |   3   |   400 |
 * |   4   |   800 |
 * |   5   |  1600 |
 * |  ...  |   ... |
 *
 * Inspired by:
 * [Exponential Backoff And Jitter](https://aws.amazon.com/blogs/architecture/exponential-backoff-and-jitter/)
 *
 * @return Backoff delay (in units matching [base] units, e.g. if [base] units are milliseconds then returned delay will be milliseconds).
 */
fun backoff(
    base: Long,
    multiplier: Float,
    retry: Int,
): Long = (base * multiplier.pow(retry - 1)).toLong()
