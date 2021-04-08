package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import coil.load
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.databinding.FragmentPokemonBinding
import hr.from.ivantoplak.pokemonapp.di.ImageRequestBuilderLambda
import hr.from.ivantoplak.pokemonapp.extensions.disable
import hr.from.ivantoplak.pokemonapp.extensions.enable
import hr.from.ivantoplak.pokemonapp.extensions.fadeIn
import hr.from.ivantoplak.pokemonapp.extensions.fadeOut
import hr.from.ivantoplak.pokemonapp.extensions.showToast
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding
import hr.from.ivantoplak.pokemonapp.managers.InternetManager
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment
import hr.from.ivantoplak.pokemonapp.viewmodel.ConnectivityViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonState
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonFragment : BaseFragment(R.layout.fragment_pokemon) {

    private val binding by viewBinding(FragmentPokemonBinding::bind)
    private val viewModel by viewModel<PokemonViewModel>()
    private val imageRequestBuilder by inject<ImageRequestBuilderLambda>()
    private val connectivityViewModel by sharedViewModel<ConnectivityViewModel>()

    @FlowPreview
    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupControls()
        setupObservers()
    }

    private fun setupControls() {

        binding.pokemonRefreshButton.setOnClickListener {
            viewModel.onRefresh()
        }

        binding.pokemonMovesButton.setOnClickListener {
            findNavController().navigate(
                PokemonFragmentDirections.showMovesScreen(
                    pokemonId = viewModel.pokemon.value?.id ?: 0
                )
            )
        }

        binding.pokemonStatsButton.setOnClickListener {
            findNavController().navigate(
                PokemonFragmentDirections.showStatsScreen(
                    pokemonId = viewModel.pokemon.value?.id ?: 0
                )
            )
        }
    }

    @FlowPreview
    private fun setupObservers() {
        observeViewState()
        observePokemon()
        observeMessages()
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

            binding.pokemonName.text = pokemon?.name

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

    private fun observeMessages() {

        viewModel.showMessage.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { showMessage ->
                if (showMessage) viewModel.showMessage(getString(R.string.error_loading_pokemons))
            }
        }

        viewModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                if (message.isNotBlank()) requireContext().showToast(message)
            }
        }
    }

    @FlowPreview
    private fun observeConnectivityStatus() {
        connectivityViewModel.status.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { status ->
                if (status == InternetManager.ConnectivityStatus.CONNECTED) {
                    if (viewModel.pokemonState.value == PokemonState.ERROR_NO_DATA) {
                        viewModel.onRefresh()
                    }
                } else {
                    requireContext().showToast(getString(R.string.no_internet_connection))
                }
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
}
