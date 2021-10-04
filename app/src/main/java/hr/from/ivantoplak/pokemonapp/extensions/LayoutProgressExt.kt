package hr.from.ivantoplak.pokemonapp.extensions

import hr.from.ivantoplak.pokemonapp.databinding.LayoutProgressBinding

private const val FADE_DURATION = 500L

fun LayoutProgressBinding.show() {
    root.fadeIn(duration = FADE_DURATION)
}

fun LayoutProgressBinding.hide() {
    root.fadeOut(duration = FADE_DURATION)
}
