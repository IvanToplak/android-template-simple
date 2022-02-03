package hr.from.ivantoplak.pokemonapp.extensions

import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.text.inSpans

/**
 * SpannableStringBuilder extension for the part of the text that has to be clickable.
 * @param click On click action
 * @param updateDrawState Setting the style of the clickable text
 * @param builderAction Usually to append the clickable text
 */
inline fun SpannableStringBuilder.click(
    crossinline click: (View) -> Unit,
    crossinline updateDrawState: (TextPaint) -> Unit = {},
    builderAction: SpannableStringBuilder.() -> Unit
) = inSpans(
    object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            updateDrawState(ds)
        }

        override fun onClick(widget: View) {
            click(widget)
        }
    },
    builderAction = builderAction
)
