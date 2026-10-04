package com.example.features.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.core.model.Pokemon
import com.example.core.ui.adapter.PokemonCardViewHolder

class PokemonListAdapter(
    private val onItemClick: (Pokemon) -> Unit
) : PagingDataAdapter<Pokemon, PokemonCardViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PokemonCardViewHolder.create(parent)

    override fun onBindViewHolder(holder: PokemonCardViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item.name, item.imageUrl) { onItemClick(item) }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem == newItem
    }
}
