package com.example.features.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.base.BaseFragment
import com.example.features.R
import com.example.features.adapter.PokemonListAdapter
import com.example.features.databinding.FragmentPokemonListBinding
import com.example.features.viewmodel.PokemonListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : BaseFragment<FragmentPokemonListBinding>() {

    private val viewModel: PokemonListViewModel by viewModel()

    private val adapter by lazy {
        PokemonListAdapter().apply {
            setOnItemClickListener { pokemon ->
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainer,
                        PokemonDetailFragment.newInstance(pokemon.name)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPokemonListBinding.inflate(inflater, container, false)

    override fun observeData() {
        viewModel.pokemonPagingData.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
        binding.recyclerViewPokemon.adapter = adapter.withLoadStateFooter(
            footer = PokemonLoadStateAdapter { adapter.retry() }
        )
    }
}
