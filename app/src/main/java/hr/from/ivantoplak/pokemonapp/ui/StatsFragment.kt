package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.from.ivantoplak.pokemonapp.adapter.StatsAdapter
import hr.from.ivantoplak.pokemonapp.databinding.FragmentStatsBinding
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment
import hr.from.ivantoplak.pokemonapp.viewmodel.StatsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StatsFragment : BaseFragment() {

    private var _binding: FragmentStatsBinding? = null

    // this property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private lateinit var statsAdapter: StatsAdapter
    private val args: StatsFragmentArgs by navArgs()
    private val viewModel by viewModel<StatsViewModel> { parametersOf(args.pokemonId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()
    }

    override fun doOnDestroyView() {
        _binding = null
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