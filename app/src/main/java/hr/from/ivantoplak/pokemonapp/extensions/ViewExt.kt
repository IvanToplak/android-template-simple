package hr.from.ivantoplak.pokemonapp.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

private const val FADE_IN_DURATION = 500L
private const val FADE_OUT_DURATION = 500L

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

fun View.fadeIn(fromAlpha: Float = 0F) {
    alpha = fromAlpha
    show()
    animate()
        .alpha(1F)
        .setDuration(FADE_IN_DURATION)
        .setListener(null)
}

fun View.fadeOut(toAlpha: Float = 0F) {
    animate()
        .alpha(toAlpha)
        .setDuration(FADE_OUT_DURATION)
        .setListener(null)
}
