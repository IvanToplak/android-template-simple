package hr.from.ivantoplak.pokemonapp.extensions

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hr.from.ivantoplak.pokemonapp.ui.components.DialogBuilder

fun Activity.hideKeyboard(target: View? = null) {
    val focus = target ?: currentFocus
    focus?.let { view ->
        val insetsController = ViewCompat.getWindowInsetsController(view)
        insetsController?.hide(WindowInsetsCompat.Type.ime())
    }
}

fun Activity.showKeyboard(target: View? = null) {
    val focus = target ?: currentFocus
    focus?.let { view ->
        val insetsController = ViewCompat.getWindowInsetsController(view)
        insetsController?.show(WindowInsetsCompat.Type.ime())
    }
}

fun Activity.dialog(setup: DialogBuilder.() -> Unit) =
    DialogBuilder(this, setup = setup).build().show()
