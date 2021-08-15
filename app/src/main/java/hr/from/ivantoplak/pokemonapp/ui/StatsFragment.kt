package hr.from.ivantoplak.pokemonapp.ui

import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment

class StatsFragment : BaseFragment(R.layout.fragment_stats) {

//    private val binding by viewBinding(FragmentStatsBinding::bind)
//    private lateinit var statsAdapter: StatsAdapter
//    private val args: StatsFragmentArgs by navArgs()
//    private val viewModel by viewModel<StatsViewModel> { parametersOf(args.pokemonId) }
//
//    override fun doOnCreate(savedInstanceState: Bundle?) {
//        setScreenTransitions()
//    }
//
//    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
//        setupActionBar(binding.toolbar)
//        setupRecyclerView()
//        setupObservers()
//    }
//
//    private fun setupRecyclerView() {
//        val layoutManager = LinearLayoutManager(context)
//        binding.statsRecyclerView.layoutManager = layoutManager
//        val dividerItemDecoration =
//            DividerItemDecoration(binding.statsRecyclerView.context, layoutManager.orientation)
//        binding.statsRecyclerView.addItemDecoration(dividerItemDecoration)
//        statsAdapter = StatsAdapter()
//        binding.statsRecyclerView.adapter = statsAdapter
//    }
//
//    private fun setupObservers() {
//        viewModel.stats.observe(viewLifecycleOwner) { stats ->
//            statsAdapter.submitList(stats)
//        }
//    }
//
//    private fun setScreenTransitions() {
//        sharedElementEnterTransition = MaterialContainerTransform()
//        sharedElementReturnTransition = null
//    }
}
