package com.example.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.model.PokemonListUiModel
import com.example.features.databinding.ItemPokemonBinding

class PokemonListAdapter :
    PagingDataAdapter<PokemonListUiModel, PokemonListAdapter.PokemonViewHolder>(DiffCallback) {

    private var onItemClickListener: ((PokemonListUiModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (PokemonListUiModel) -> Unit) {
        onItemClickListener = listener
    }

    inner class PokemonViewHolder(
        private val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PokemonListUiModel) = binding.apply {
            tvName.text = item.name
            Glide.with(ivPokemon).load(item.imageUrl).into(ivPokemon)
            root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<PokemonListUiModel>() {
            override fun areItemsTheSame(
                oldItem: PokemonListUiModel,
                newItem: PokemonListUiModel
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: PokemonListUiModel,
                newItem: PokemonListUiModel
            ): Boolean = oldItem == newItem
        }
    }
}
