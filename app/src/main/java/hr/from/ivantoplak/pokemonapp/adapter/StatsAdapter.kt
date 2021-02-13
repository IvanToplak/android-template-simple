package hr.from.ivantoplak.pokemonapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.from.ivantoplak.pokemonapp.databinding.ListItemStatBinding
import hr.from.ivantoplak.pokemonapp.model.StatViewData

class StatsAdapter : ListAdapter<StatViewData, StatsAdapter.ViewHolder>(StatsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: ListItemStatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StatViewData) {
            binding.statNameText.text = item.name
            binding.statValueText.text = item.value.toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemStatBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

private class StatsDiffCallback : DiffUtil.ItemCallback<StatViewData>() {

    override fun areItemsTheSame(oldItem: StatViewData, newItem: StatViewData): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: StatViewData, newItem: StatViewData): Boolean {
        return oldItem == newItem
    }
}