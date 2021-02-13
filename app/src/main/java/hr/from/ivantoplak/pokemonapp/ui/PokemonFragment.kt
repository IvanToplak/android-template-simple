package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.databinding.FragmentPokemonBinding
import hr.from.ivantoplak.pokemonapp.extensions.disable
import hr.from.ivantoplak.pokemonapp.extensions.enable
import hr.from.ivantoplak.pokemonapp.extensions.hide
import hr.from.ivantoplak.pokemonapp.extensions.show
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import hr.from.ivantoplak.pokemonapp.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PokemonFragment : Fragment() {

    private lateinit var binding: FragmentPokemonBinding
    private val viewModel by sharedViewModel<PokemonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupControls()
        setupObservers(view)
    }

    private fun setupControls() {

        binding.pokemonRefreshButton.setOnClickListener {
            viewModel.onRefresh()
        }

        binding.pokemonMovesButton.setOnClickListener {
            findNavController().navigate(PokemonFragmentDirections.actionPokemonFragmentToMovesFragment())
        }

        binding.pokemonStatsButton.setOnClickListener {
            findNavController().navigate(PokemonFragmentDirections.actionPokemonFragmentToStatsFragment())
        }
    }

    private fun setupObservers(view: View) {

        // view state
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            viewState?.let {
                setViewState(it)
            }
        }

        // images
        viewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->

            binding.pokemonName.text = pokemon.name

            // foreground image
            Glide.with(view).load(pokemon?.frontSpriteUrl)
                .error(R.drawable.image_placeholder)
                .into(binding.pokemonSpriteForeground)

            // background image
            Glide.with(view).load(pokemon?.backSpriteUrl)
                .error(R.drawable.image_placeholder)
                .into(binding.pokemonSpriteBackground)
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
            ViewState.INITIAL_LOADING -> {
                binding.apply {
                    loadingProgressBar.show()
                    pokemonName.hide()
                    pokemonSpriteForeground.hide()
                    pokemonSpriteBackground.hide()
                    pokemonMovesButton.hide()
                    pokemonStatsButton.hide()
                    pokemonRefreshButton.hide()
                }
            }

            ViewState.LOADING -> {
                binding.apply {
                    loadingProgressBar.show()
                    pokemonName.show()
                    pokemonSpriteForeground.show()
                    pokemonSpriteBackground.show()
                    pokemonMovesButton.show()
                    pokemonMovesButton.disable()
                    pokemonStatsButton.show()
                    pokemonStatsButton.disable()
                    pokemonRefreshButton.show()
                    pokemonRefreshButton.disable()
                }
            }

            ViewState.READY -> {
                binding.apply {
                    loadingProgressBar.hide()
                    pokemonName.show()
                    pokemonSpriteForeground.show()
                    pokemonSpriteBackground.show()
                    pokemonMovesButton.show()
                    pokemonMovesButton.enable()
                    pokemonStatsButton.show()
                    pokemonStatsButton.enable()
                    pokemonRefreshButton.show()
                    pokemonRefreshButton.enable()
                }
            }

            ViewState.EMPTY -> {
                binding.apply {
                    loadingProgressBar.hide()
                    pokemonName.hide()
                    pokemonSpriteForeground.hide()
                    pokemonSpriteBackground.hide()
                    pokemonMovesButton.hide()
                    pokemonStatsButton.hide()
                    pokemonRefreshButton.show()
                    pokemonRefreshButton.enable()
                }
            }
        }
    }
}
