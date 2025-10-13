package com.example.features.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.model.PokemonDetailUIModel
import com.example.features.adapter.EvolutionAdapter
import com.example.features.databinding.FragmentPokemonEvolutionBinding

class PokemonEvolutionFragment : BaseFragment<FragmentPokemonEvolutionBinding>() {
    private val pokemonData by lazy {
        arguments?.getParcelable<PokemonDetailUIModel>(ARG_PARAM1)?.evolutionList ?: emptyList()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonEvolutionBinding.inflate(inflater, container, false)

    override fun observeData() {
        super.observeData()
        setupUI()
    }

    private fun setupUI() {
        binding.recyclerEvolution.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = EvolutionAdapter(pokemonData)
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        @JvmStatic
        fun newInstance(param1: PokemonDetailUIModel?) =
            PokemonEvolutionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}