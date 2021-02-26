package hr.from.ivantoplak.pokemonapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.from.ivantoplak.pokemonapp.databinding.ListItemMoveBinding
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding
import hr.from.ivantoplak.pokemonapp.ui.model.MoveViewData

class MovesAdapter : ListAdapter<MoveViewData, MovesAdapter.ViewHolder>(MovesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.viewBinding(ListItemMoveBinding::inflate))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemMoveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoveViewData) {
            binding.moveText.text = item.name
        }
    }

    private class MovesDiffCallback : DiffUtil.ItemCallback<MoveViewData>() {

        override fun areItemsTheSame(oldItem: MoveViewData, newItem: MoveViewData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoveViewData, newItem: MoveViewData): Boolean {
            return oldItem == newItem
        }
    }
}
