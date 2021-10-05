package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.databinding.FragmentPokemonBinding
import hr.from.ivantoplak.pokemonapp.di.ImageRequestBuilderLambda
import hr.from.ivantoplak.pokemonapp.extensions.dialog
import hr.from.ivantoplak.pokemonapp.extensions.disable
import hr.from.ivantoplak.pokemonapp.extensions.enable
import hr.from.ivantoplak.pokemonapp.extensions.fadeIn
import hr.from.ivantoplak.pokemonapp.extensions.fadeOut
import hr.from.ivantoplak.pokemonapp.extensions.hide
import hr.from.ivantoplak.pokemonapp.extensions.observeEvent
import hr.from.ivantoplak.pokemonapp.extensions.setupActionBar
import hr.from.ivantoplak.pokemonapp.extensions.show
import hr.from.ivantoplak.pokemonapp.extensions.showToast
import hr.from.ivantoplak.pokemonapp.extensions.titleCaseFirstChar
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding
import hr.from.ivantoplak.pokemonapp.managers.InternetManager
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment
import hr.from.ivantoplak.pokemonapp.viewmodel.ConnectivityViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonState
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonFragment : BaseFragment(R.layout.fragment_pokemon) {

    private val binding by viewBinding(FragmentPokemonBinding::bind)
    private val viewModel by viewModel<PokemonViewModel>()
    private val imageRequestBuilder by inject<ImageRequestBuilderLambda>()
    private val connectivityViewModel by sharedViewModel<ConnectivityViewModel>()

    override fun doOnCreate(savedInstanceState: Bundle?) {
        setScreenTransitions()
    }

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupActionBar(binding.toolbar)
        setupControls()
        setupObservers()
    }

    private fun setupControls() {

        binding.pokemonRefreshButton.setOnClickListener {
            viewModel.onRefresh()
        }

        binding.pokemonMovesButton.setOnClickListener { button ->
            val extras =
                FragmentNavigatorExtras(button to getString(R.string.pokemon_to_pokemon_moves_transition))
            findNavController().navigate(
                PokemonFragmentDirections.showMovesScreen(
                    pokemonId = viewModel.pokemon.value?.id ?: 0
                ),
                extras
            )
        }

        binding.pokemonStatsButton.setOnClickListener { button ->
            val extras =
                FragmentNavigatorExtras(button to getString(R.string.pokemon_to_pokemon_stats_transition))
            findNavController().navigate(
                PokemonFragmentDirections.showStatsScreen(
                    pokemonId = viewModel.pokemon.value?.id ?: 0
                ),
                extras
            )
        }
    }

    private fun setupObservers() {
        observeViewState()
        observePokemon()
        observeErrorMessages()
        observeConnectivityStatus()
    }

    private fun observeViewState() {
        viewModel.pokemonState.observe(viewLifecycleOwner) { viewState ->
            viewState?.let {
                setViewState(it)
            }
        }
    }

    private fun observePokemon() {
        viewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->

            binding.pokemonName.text = pokemon?.name?.titleCaseFirstChar()

            // foreground image
            binding.pokemonSpriteForeground.load(
                uri = pokemon?.frontSpriteUrl,
                builder = imageRequestBuilder
            )
            // background image
            binding.pokemonSpriteBackground.load(
                uri = pokemon?.backSpriteUrl,
                builder = imageRequestBuilder
            )
        }
    }

    private fun observeErrorMessages() {

        viewModel.showErrorMessage.observeEvent(viewLifecycleOwner) { showMessage ->
            if (showMessage) {
                showErrorMessageDialog()
            }
        }
    }

    private fun observeConnectivityStatus() {
        connectivityViewModel.status.observeEvent(viewLifecycleOwner) { status ->
            if (status == InternetManager.ConnectivityStatus.CONNECTED) {
                if (viewModel.pokemonState.value == PokemonState.ERROR_NO_DATA) {
                    viewModel.onRefresh()
                }
            } else {
                requireContext().showToast(getString(R.string.no_internet_connection))
            }
        }
    }

    private fun setViewState(pokemonState: PokemonState) {
        when (pokemonState) {
            PokemonState.LOADING -> {
                setLoadingState()
                fadeOut()
            }

            PokemonState.ERROR_NO_DATA -> {
                setNoDataState()
                fadeIn()
            }

            PokemonState.ERROR_HAS_DATA -> {
                setHasDataState()
                fadeIn()
            }

            PokemonState.SUCCESS -> {
                setHasDataState()
                fadeIn()
            }
        }
    }

    private fun setLoadingState() {
        binding.apply {
            loadingProgressBar.show()
            pokemonMovesButton.disable()
            pokemonStatsButton.disable()
            pokemonRefreshButton.disable()
        }
    }

    private fun setNoDataState() {
        binding.apply {
            loadingProgressBar.hide()
            pokemonMovesButton.disable()
            pokemonStatsButton.disable()
            pokemonRefreshButton.enable()
        }
    }

    private fun setHasDataState() {
        binding.apply {
            loadingProgressBar.hide()
            pokemonMovesButton.enable()
            pokemonStatsButton.enable()
            pokemonRefreshButton.enable()
        }
    }

    private fun fadeIn() {
        val fromAlpha = 0.5F
        binding.apply {
            pokemonName.fadeIn(fromAlpha)
            pokemonSpriteForeground.fadeIn(fromAlpha)
            pokemonSpriteBackground.fadeIn(fromAlpha)
            pokemonMovesButton.fadeIn(fromAlpha)
            pokemonStatsButton.fadeIn(fromAlpha)
            pokemonRefreshButton.fadeIn(fromAlpha)
        }
    }

    private fun fadeOut() {
        val toAlpha = 0.5F
        binding.apply {
            pokemonName.fadeOut(toAlpha)
            pokemonSpriteForeground.fadeOut(toAlpha)
            pokemonSpriteBackground.fadeOut(toAlpha)
            pokemonMovesButton.fadeOut(toAlpha)
            pokemonStatsButton.fadeOut(toAlpha)
            pokemonRefreshButton.fadeOut(toAlpha)
        }
    }

    private fun setScreenTransitions() {
        enterTransition = MaterialFadeThrough()
        reenterTransition = MaterialElevationScale(true)
        exitTransition = MaterialElevationScale(false)
    }

    private fun showErrorMessageDialog() {
        dialog {
            titleRes = R.string.error_dialog_title
            contentRes = R.string.error_dialog_body
            positiveTextRes = R.string.error_dialog_button_retry
            positiveAction = {
                viewModel.onRefresh()
            }
            negativeTextRes = R.string.error_dialog_button_cancel
            negativeAction = { }
        }
    }
}
