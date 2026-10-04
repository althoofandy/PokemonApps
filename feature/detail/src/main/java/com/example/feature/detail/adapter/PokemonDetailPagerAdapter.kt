package com.example.feature.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.feature.detail.tab.PokemonAboutFragment
import com.example.feature.detail.tab.PokemonBaseStatsFragment
import com.example.feature.detail.tab.PokemonEvolutionFragment

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
