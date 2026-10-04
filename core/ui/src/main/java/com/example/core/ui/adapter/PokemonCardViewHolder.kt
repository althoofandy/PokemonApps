package com.example.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.ui.databinding.ItemPokemonBinding

class PokemonCardViewHolder private constructor(
    private val binding: ItemPokemonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String, imageUrl: String, onClick: () -> Unit) = with(binding) {
        tvName.text = name
        Glide.with(ivPokemon).load(imageUrl).into(ivPokemon)
        root.setOnClickListener { onClick() }
    }

    companion object {
        fun create(parent: ViewGroup): PokemonCardViewHolder = PokemonCardViewHolder(
            ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}
