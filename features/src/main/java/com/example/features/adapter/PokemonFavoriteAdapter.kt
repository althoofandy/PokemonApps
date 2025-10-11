package com.example.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.local.PokemonFavoriteEntity
import com.example.features.databinding.ItemPokemonBinding

class FavoritePokemonAdapter :
    ListAdapter<PokemonFavoriteEntity, FavoritePokemonAdapter.ViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonFavoriteEntity) = binding.apply {
            tvName.text = pokemon.name
            Glide.with(binding.ivPokemon.context)
                .load(pokemon.imageUrl)
                .into(binding.ivPokemon)
            root.setOnClickListener {
                onItemClickListener?.invoke(pokemon.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<PokemonFavoriteEntity>() {
        override fun areItemsTheSame(
            oldItem: PokemonFavoriteEntity,
            newItem: PokemonFavoriteEntity
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PokemonFavoriteEntity,
            newItem: PokemonFavoriteEntity
        ) = oldItem == newItem
    }
}
