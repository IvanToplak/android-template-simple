package hr.from.ivantoplak.pokemonapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import hr.from.ivantoplak.pokemonapp.R
import hr.from.ivantoplak.pokemonapp.adapter.MovesAdapter
import hr.from.ivantoplak.pokemonapp.databinding.FragmentMovesBinding
import hr.from.ivantoplak.pokemonapp.extensions.setupActionBar
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding
import hr.from.ivantoplak.pokemonapp.ui.common.BaseFragment
import hr.from.ivantoplak.pokemonapp.viewmodel.MovesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovesFragment : BaseFragment(R.layout.fragment_moves) {

    private val binding by viewBinding(FragmentMovesBinding::bind)
    private lateinit var movesAdapter: MovesAdapter
    private val args: MovesFragmentArgs by navArgs()
    private val viewModel by viewModel<MovesViewModel> { parametersOf(args.pokemonId) }

    override fun doOnCreate(savedInstanceState: Bundle?) {
        setScreenTransitions()
    }

    override fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {
        setupActionBar(binding.toolbar)
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

    private fun setScreenTransitions() {
        sharedElementEnterTransition = MaterialContainerTransform()
        sharedElementReturnTransition = null
    }
}
