package com.example.feature.favorite.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.core.model.FavoritePokemon
import com.example.core.ui.adapter.PokemonCardViewHolder

class FavoritePokemonAdapter(
    private val onItemClick: (FavoritePokemon) -> Unit
) : ListAdapter<FavoritePokemon, PokemonCardViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PokemonCardViewHolder.create(parent)

    override fun onBindViewHolder(holder: PokemonCardViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.name, item.imageUrl) { onItemClick(item) }
    }

    private object DiffCallback : DiffUtil.ItemCallback<FavoritePokemon>() {
        override fun areItemsTheSame(oldItem: FavoritePokemon, newItem: FavoritePokemon) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavoritePokemon, newItem: FavoritePokemon) =
            oldItem == newItem
    }
}
