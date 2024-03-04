package hr.from.ivantoplak.pokemonapp.common.coroutines

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Context.broadcastReceiverFlow(intentFilter: IntentFilter): Flow<Intent> = callbackFlow {
    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                trySend(intent)
            }
        }
    }
    val flags = ContextCompat.RECEIVER_NOT_EXPORTED
    ContextCompat.registerReceiver(applicationContext, broadcastReceiver, intentFilter, flags)
    awaitClose { applicationContext.unregisterReceiver(broadcastReceiver) }
}
