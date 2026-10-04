package com.example.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.model.PokemonEvolution
import com.example.features.databinding.ItemEvolutionBinding

class EvolutionAdapter(private val evolutions: List<PokemonEvolution>) :
    RecyclerView.Adapter<EvolutionAdapter.EvolutionViewHolder>() {

    inner class EvolutionViewHolder(val binding: ItemEvolutionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionViewHolder {
        val binding =
            ItemEvolutionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EvolutionViewHolder(binding)
    }

    override fun getItemCount(): Int = evolutions.size

    override fun onBindViewHolder(holder: EvolutionViewHolder, position: Int) {
        val evo = evolutions[position]
        holder.binding.tvName.text = evo.name
        holder.binding.tvLevel.text = evo.minLevel?.toString().orEmpty()
        Glide.with(holder.binding.ivPokemon).load(evo.imageUrl).into(holder.binding.ivPokemon)
    }
}
