package com.example.features.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.core.model.PokemonDetailUIModel
import com.example.features.ui.detail.PokemonAboutFragment
import com.example.features.ui.detail.PokemonBaseStatsFragment
import com.example.features.ui.detail.PokemonEvolutionFragment

class PokemonDetailPagerAdapter(
    fragment: Fragment,
    private val pokemon: PokemonDetailUIModel
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PokemonAboutFragment.newInstance(pokemon)
            1 -> PokemonBaseStatsFragment.newInstance(pokemon)
            2 -> PokemonEvolutionFragment.newInstance(pokemon)
            else -> Fragment()
        }
    }
}
