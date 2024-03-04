package hr.from.ivantoplak.pokemonapp.common.location

import android.content.Context
import android.content.Intent
import android.provider.Settings

interface LocationManager {

    fun isGpsEnabled(): Boolean
}

class LocationManagerImpl(private val context: Context) : LocationManager {
    override fun isGpsEnabled(): Boolean =
        getAndroidLocationManager()?.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
            ?: false

    private fun getAndroidLocationManager() =
        context.getSystemService(Context.LOCATION_SERVICE) as? android.location.LocationManager
}

fun Context.enableGps() {
    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}
