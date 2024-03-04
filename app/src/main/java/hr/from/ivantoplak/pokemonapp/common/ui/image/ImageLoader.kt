package hr.from.ivantoplak.pokemonapp.common.ui.image

import android.content.Context
import coil.ImageLoader
import hr.from.ivantoplak.pokemonapp.R

/**
 * Creating image loader instance from the context.
 * There should be only one instance per app.
 * Caching is supported by default, but can be customised here.
 * Logger should be enabled only for debug builds, test performance only on release builds.
 */
fun Context.createImageLoader(): ImageLoader = ImageLoader
    .Builder(this)
    .respectCacheHeaders(false)
    // Enable logging for debug builds and when there is something to debug
    // .logger(if (BuildConfig.DEBUG) DebugLogger() else null)
    .crossfade(true)
    .fallback(R.drawable.image_placeholder)
    .error(R.drawable.image_placeholder)
    .build()
