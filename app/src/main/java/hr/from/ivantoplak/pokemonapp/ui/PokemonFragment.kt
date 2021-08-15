package hr.from.ivantoplak.pokemonapp.ui

import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment

class PokemonFragment : BaseFragment(R.layout.fragment_pokemon) {
//
//    private val binding by viewBinding(FragmentPokemonBinding::bind)
//    private val viewModel by viewModel<PokemonViewModel>()
//    private val imageRequestBuilder by inject<ImageRequestBuilderLambda>()
//    private val connectivityViewModel by sharedViewModel<ConnectivityViewModel>()
//
//    override fun doOnCreate(savedInstanceState: Bundle?) {
//        setScreenTransitions()
//    }
//
//    @FlowPreview
//    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
//        setupActionBar(binding.toolbar)
//        setupControls()
//        setupObservers()
//    }
//
//    private fun setupControls() {
//
//        binding.pokemonRefreshButton.setOnClickListener {
//            viewModel.onRefresh()
//        }
//
//        binding.pokemonMovesButton.setOnClickListener { button ->
//            val extras =
//                FragmentNavigatorExtras(button to getString(R.string.pokemon_to_pokemon_moves_transition))
//            findNavController().navigate(
//                PokemonFragmentDirections.showMovesScreen(
//                    pokemonId = viewModel.pokemon.value?.id ?: 0
//                ),
//                extras
//            )
//        }
//
//        binding.pokemonStatsButton.setOnClickListener { button ->
//            val extras =
//                FragmentNavigatorExtras(button to getString(R.string.pokemon_to_pokemon_stats_transition))
//            findNavController().navigate(
//                PokemonFragmentDirections.showStatsScreen(
//                    pokemonId = viewModel.pokemon.value?.id ?: 0
//                ),
//                extras
//            )
//        }
//    }
//
//    @FlowPreview
//    private fun setupObservers() {
//        observeViewState()
//        observePokemon()
//        observeMessages()
//        observeConnectivityStatus()
//    }
//
//    private fun observeViewState() {
//        viewModel.pokemonState.observe(viewLifecycleOwner) { viewState ->
//            viewState?.let {
//                setViewState(it)
//            }
//        }
//    }
//
//    private fun observePokemon() {
//        viewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
//
//            binding.pokemonName.text = pokemon?.name
//
//            // foreground image
//            binding.pokemonSpriteForeground.load(
//                uri = pokemon?.frontSpriteUrl,
//                builder = imageRequestBuilder
//            )
//            // background image
//            binding.pokemonSpriteBackground.load(
//                uri = pokemon?.backSpriteUrl,
//                builder = imageRequestBuilder
//            )
//        }
//    }
//
//    private fun observeMessages() {
//
//        viewModel.showMessage.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { showMessage ->
//                if (showMessage) viewModel.showMessage(getString(R.string.error_loading_pokemons))
//            }
//        }
//
//        viewModel.message.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { message ->
//                if (message.isNotBlank()) requireContext().showToast(message)
//            }
//        }
//    }
//
//    @FlowPreview
//    private fun observeConnectivityStatus() {
//        connectivityViewModel.status.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { status ->
//                if (status == InternetManager.ConnectivityStatus.CONNECTED) {
//                    if (viewModel.pokemonState.value == PokemonState.ERROR_NO_DATA) {
//                        viewModel.onRefresh()
//                    }
//                } else {
//                    requireContext().showToast(getString(R.string.no_internet_connection))
//                }
//            }
//        }
//    }
//
//    private fun setViewState(pokemonState: PokemonState) {
//        when (pokemonState) {
//            PokemonState.LOADING -> {
//                setLoadingState()
//                fadeOut()
//            }
//
//            PokemonState.ERROR_NO_DATA -> {
//                setNoDataState()
//                fadeIn()
//            }
//
//            PokemonState.ERROR_HAS_DATA -> {
//                setHasDataState()
//                fadeIn()
//            }
//
//            PokemonState.SUCCESS -> {
//                setHasDataState()
//                fadeIn()
//            }
//        }
//    }
//
//    private fun setLoadingState() {
//        binding.apply {
//            loadingProgressBar.show()
//            pokemonMovesButton.disable()
//            pokemonStatsButton.disable()
//            pokemonRefreshButton.disable()
//        }
//    }
//
//    private fun setNoDataState() {
//        binding.apply {
//            loadingProgressBar.hide()
//            pokemonMovesButton.disable()
//            pokemonStatsButton.disable()
//            pokemonRefreshButton.enable()
//        }
//    }
//
//    private fun setHasDataState() {
//        binding.apply {
//            loadingProgressBar.hide()
//            pokemonMovesButton.enable()
//            pokemonStatsButton.enable()
//            pokemonRefreshButton.enable()
//        }
//    }
//
//    private fun fadeIn() {
//        val fromAlpha = 0.5F
//        binding.apply {
//            pokemonName.fadeIn(fromAlpha)
//            pokemonSpriteForeground.fadeIn(fromAlpha)
//            pokemonSpriteBackground.fadeIn(fromAlpha)
//            pokemonMovesButton.fadeIn(fromAlpha)
//            pokemonStatsButton.fadeIn(fromAlpha)
//            pokemonRefreshButton.fadeIn(fromAlpha)
//        }
//    }
//
//    private fun fadeOut() {
//        val toAlpha = 0.5F
//        binding.apply {
//            pokemonName.fadeOut(toAlpha)
//            pokemonSpriteForeground.fadeOut(toAlpha)
//            pokemonSpriteBackground.fadeOut(toAlpha)
//            pokemonMovesButton.fadeOut(toAlpha)
//            pokemonStatsButton.fadeOut(toAlpha)
//            pokemonRefreshButton.fadeOut(toAlpha)
//        }
//    }
//
//    private fun setScreenTransitions() {
//        enterTransition = MaterialFadeThrough()
//        reenterTransition = MaterialElevationScale(true)
//        exitTransition = MaterialElevationScale(false)
//    }
}
