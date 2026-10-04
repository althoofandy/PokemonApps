package com.example.feature.detail.tab

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.model.PokemonDetail
import com.example.core.ui.base.BaseFragment
import com.example.core.ui.state.onSuccess
import com.example.core.ui.utils.PokemonSpeciesColor
import com.example.feature.detail.PokemonDetailViewModel
import com.example.feature.detail.R
import com.example.feature.detail.databinding.FragmentPokemonBaseStatsBinding
import com.example.feature.detail.databinding.ItemStatRowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonBaseStatsFragment : BaseFragment<FragmentPokemonBaseStatsBinding>() {

    private val viewModel: PokemonDetailViewModel by viewModel(
        ownerProducer = { requireParentFragment() }
    )

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonBaseStatsBinding.inflate(inflater, container, false)

    override fun observeData() {
        viewModel.pokemonDetail.collectWithLifecycle { state ->
            state.onSuccess(::setupBaseStats)
        }
    }

    private fun setupBaseStats(pokemon: PokemonDetail) = with(binding) {
        val speciesColor = PokemonSpeciesColor.fromString(pokemon.speciesColor).color
        tvTitle.text = getString(R.string.base_stats)
        statsContainer.removeAllViews()

        pokemon.stats.forEach { (name, value) ->
            val itemStat = ItemStatRowBinding.inflate(layoutInflater, statsContainer, false)
            itemStat.tvStatName.text = name.uppercase()
            itemStat.pbStat.progress = value
            itemStat.pbStat.progressTintList = ColorStateList.valueOf(speciesColor)
            itemStat.tvStatValue.text = value.toString()
            statsContainer.addView(itemStat.root)
        }
    }
}
