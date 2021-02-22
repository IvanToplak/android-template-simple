package hr.from.ivantoplak.pokemonapp.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doOnViewCreated(view, savedInstanceState)
    }

    protected open fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {}

    final override fun onDestroyView() {
        doOnDestroyView()
        super.onDestroyView()
    }

    protected open fun doOnDestroyView() {}
}