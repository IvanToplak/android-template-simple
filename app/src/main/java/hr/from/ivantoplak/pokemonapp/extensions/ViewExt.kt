package hr.from.ivantoplak.pokemonapp.extensions

import android.view.View
import android.view.View.*


fun View.show(show: Boolean = true) {
    if (visibility == VISIBLE && show) return
    if ((visibility == GONE || visibility == INVISIBLE) && !show) return
    visibility = if (show) VISIBLE else INVISIBLE
}

fun View.hide() {
    if (visibility == INVISIBLE) return
    visibility = INVISIBLE
}

fun View.enable() {
    if (isEnabled) return
    isEnabled = true
}

fun View.disable() {
    if (!isEnabled) return
    isEnabled = false
}