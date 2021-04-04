package hr.from.ivantoplak.pokemonapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.from.ivantoplak.pokemonapp.databinding.ListItemStatBinding
import hr.from.ivantoplak.pokemonapp.extensions.viewBinding
import hr.from.ivantoplak.pokemonapp.ui.model.StatViewData

class StatsAdapter : ListAdapter<StatViewData, StatsAdapter.ViewHolder>(StatsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.viewBinding(ListItemStatBinding::inflate))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemStatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StatViewData) {
            binding.statNameText.text = item.name
            binding.statValueText.text = item.value.toString()
        }
    }

    private class StatsDiffCallback : DiffUtil.ItemCallback<StatViewData>() {

        override fun areItemsTheSame(oldItem: StatViewData, newItem: StatViewData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StatViewData, newItem: StatViewData): Boolean {
            return oldItem == newItem
        }
    }
}
