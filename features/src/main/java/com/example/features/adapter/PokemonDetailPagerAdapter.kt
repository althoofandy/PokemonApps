package com.example.features.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.features.ui.detail.PokemonAboutFragment
import com.example.features.ui.detail.PokemonBaseStatsFragment
import com.example.features.ui.detail.PokemonEvolutionFragment

class PokemonDetailPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PokemonAboutFragment()
            1 -> PokemonBaseStatsFragment()
            else -> PokemonEvolutionFragment()
        }
    }
}
