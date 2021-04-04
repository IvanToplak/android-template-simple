package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.adapter.StatsAdapter
import hr.from.ivantoplak.pokemonapp.databinding.FragmentStatsBinding
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StatsFragment : BaseFragment(R.layout.fragment_stats) {

    private val binding by viewBinding(FragmentStatsBinding::bind)
    private lateinit var statsAdapter: StatsAdapter
    private val args: StatsFragmentArgs by navArgs()
    private val viewModel by viewModel<StatsViewModel> { parametersOf(args.pokemonId) }

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.statsRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration =
            DividerItemDecoration(binding.statsRecyclerView.context, layoutManager.orientation)
        binding.statsRecyclerView.addItemDecoration(dividerItemDecoration)
        statsAdapter = StatsAdapter()
        binding.statsRecyclerView.adapter = statsAdapter
    }

    private fun setupObservers() {
        viewModel.stats.observe(viewLifecycleOwner) { stats ->
            statsAdapter.submitList(stats)
        }
    }
}
