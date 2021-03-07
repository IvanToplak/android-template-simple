package hr.from.ivantoplak.pokemonapp.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.show() {
    if (visibility == VISIBLE) return
    visibility = VISIBLE
}

fun View.hide() {
    if (visibility == INVISIBLE) return
    visibility = INVISIBLE
}

fun View.remove() {
    if (visibility == GONE) return
    visibility = GONE
}

fun View.enable() {
    if (isEnabled) return
    isEnabled = true
}

fun View.disable() {
    if (!isEnabled) return
    isEnabled = false
}