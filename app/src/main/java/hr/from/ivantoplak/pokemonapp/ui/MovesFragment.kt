package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.from.ivantoplak.pokemonapp.adapter.MovesAdapter
import hr.from.ivantoplak.pokemonapp.databinding.FragmentMovesBinding
import hr.from.ivantoplak.pokemonapp.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovesFragment : Fragment() {

    private lateinit var binding: FragmentMovesBinding
    private lateinit var movesAdapter: MovesAdapter
    private val viewModel by sharedViewModel<PokemonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.movesRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration =
            DividerItemDecoration(binding.movesRecyclerView.context, layoutManager.orientation)
        binding.movesRecyclerView.addItemDecoration(dividerItemDecoration)
        movesAdapter = MovesAdapter()
        binding.movesRecyclerView.adapter = movesAdapter
    }

    private fun setupObservers() {
        viewModel.moves.observe(viewLifecycleOwner) { moves ->
            movesAdapter.submitList(moves)
        }
    }
}