package hr.from.ivantoplak.pokemonapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import org.koin.androidx.scope.ScopeFragment

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : ScopeFragment(contentLayoutId) {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doOnCreate(savedInstanceState)
    }

    protected open fun doOnCreate(savedInstanceState: Bundle?) {}

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doOnViewCreated(view, savedInstanceState)
    }

    protected open fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {}

    // use SavedStateHandle in a ViewModel
    final override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    final override fun onStart() {
        super.onStart()
        doOnStart()
    }

    protected open fun doOnStart() {}

    final override fun onResume() {
        super.onResume()
        doOnResume()
    }

    protected open fun doOnResume() {}

    final override fun onPause() {
        doOnPause()
        super.onPause()
    }

    protected open fun doOnPause() {}

    override fun onStop() {
        doOnStop()
        super.onStop()
    }

    protected open fun doOnStop() {}

    // use SavedStateHandle in a ViewModel
    final override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    final override fun onDestroyView() {
        doOnDestroyView()
        super.onDestroyView()
    }

    protected open fun doOnDestroyView() {}

    final override fun onDestroy() {
        doOnDestroy()
        super.onDestroy()
    }

    protected open fun doOnDestroy() {}
}
