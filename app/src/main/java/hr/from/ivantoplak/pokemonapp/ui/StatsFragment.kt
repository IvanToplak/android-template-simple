package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.from.ivantoplak.pokemonapp.adapter.StatsAdapter
import hr.from.ivantoplak.pokemonapp.databinding.FragmentStatsBinding
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private lateinit var statsAdapter: StatsAdapter
    private val args: StatsFragmentArgs by navArgs()
    private val viewModel by viewModel<StatsViewModel> { parametersOf(args.pokemonId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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