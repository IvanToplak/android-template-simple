package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.databinding.FragmentPokemonBinding
import hr.from.ivantoplak.pokemonapp.di.ImageRequestBuilderLambda
import hr.from.ivantoplak.pokemonapp.extensions.disable
import hr.from.ivantoplak.pokemonapp.extensions.enable
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonFragment : BaseFragment(R.layout.fragment_pokemon) {

    private val binding by viewBinding(FragmentPokemonBinding::bind)
    private val viewModel by viewModel<PokemonViewModel>()
    private val imageRequestBuilder by inject<ImageRequestBuilderLambda>()

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
                PokemonFragmentDirections.actionPokemonFragmentToMovesFragment(
                    pokemonId = viewModel.pokemon.value?.id ?: 0
                )
            )
        }

        binding.pokemonStatsButton.setOnClickListener {
            findNavController().navigate(
                PokemonFragmentDirections.actionPokemonFragmentToStatsFragment(
                    pokemonId = viewModel.pokemon.value?.id ?: 0
                )
            )
        }
    }

    private fun setupObservers() {

        // view state
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            viewState?.let {
                setViewState(it)
            }
        }

        // images and text views
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

        // error messages
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNullOrBlank()) return@observe
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.doneShowingToastMessage()
        }
    }

    private fun setViewState(viewState: ViewState) {
        when (viewState) {
            ViewState.LOADING -> {
                setLoadingState()
            }
            ViewState.ERROR_NO_DATA -> {
                setNoDataState()
            }

            ViewState.ERROR_HAS_DATA -> {
                setHasDataState()
            }

            ViewState.SUCCESS -> {
                setHasDataState()
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
}
