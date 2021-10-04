package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialFadeThrough
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.databinding.FragmentSplashBinding
import hr.from.ivantoplak.pokemonapp.extensions.hideNavBar
import hr.from.ivantoplak.pokemonapp.extensions.showNavBar
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment
import kotlinx.coroutines.delay

private const val FADE_OUT_DURATION = 1000L
private const val SHOW_SCREEN_DURATION = 500L

/**
 * Splash fragment represents app's splash screen with logo and background.
 * After short delay it navigates to main flow, if the user is logged in, or to auth flow otherwise.
 */
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun doOnCreate(savedInstanceState: Bundle?) {
        setScreenTransitions()
    }

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        removeStatusBarColor()
        hideNavBar(binding.root)
    }

    override fun doOnResume() {
        runAfterDelay {
            findNavController().navigate(SplashFragmentDirections.showPokemonScreen())
            restoreStatusBarColor()
        }
    }

    override fun doOnDestroyView() {
        showNavBar(binding.root)
    }

    private fun runAfterDelay(delay: Long = SHOW_SCREEN_DURATION, action: () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(delay)
            action()
        }
    }

    private fun setScreenTransitions() {
        exitTransition = MaterialFadeThrough().apply { duration = FADE_OUT_DURATION }
    }

    private fun removeStatusBarColor() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun restoreStatusBarColor() {
        requireActivity().window.statusBarColor =
            MaterialColors.getColor(binding.root, R.attr.colorPrimaryVariant)
    }
}
