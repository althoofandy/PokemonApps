package com.example.features.ui.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.base.BaseFragment
import com.example.core.model.PokemonDetailUIModel
import com.example.features.R
import com.example.features.databinding.FragmentPokemonBaseStatsBinding
import com.example.features.databinding.ItemStatRowBinding

class PokemonBaseStatsFragment : BaseFragment<FragmentPokemonBaseStatsBinding>() {

    private val pokemonData by lazy {
        arguments?.getParcelable<PokemonDetailUIModel>(ARG_PARAM1)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonBaseStatsBinding.inflate(inflater, container, false)

    override fun observeData() {
        super.observeData()
        pokemonData?.let { setupBaseStats(it) }
    }

    private fun setupBaseStats(data: PokemonDetailUIModel) {
        binding.tvTitle.text = getString(R.string.base_stats)
        binding.containerStats.removeAllViews()

        data.stats.forEach { (name, value) ->
            val speciesColor = data.color

            val itemStat = ItemStatRowBinding.inflate(layoutInflater, binding.containerStats, false)
            itemStat.tvStatName.text = name.uppercase()
            itemStat.pbStat.progress = value
            itemStat.pbStat.progressTintList = ColorStateList.valueOf(speciesColor)
            itemStat.tvStatValue.text = value.toString()

            binding.containerStats.addView(itemStat.root)
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        @JvmStatic
        fun newInstance(param1: PokemonDetailUIModel?) =
            PokemonBaseStatsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}