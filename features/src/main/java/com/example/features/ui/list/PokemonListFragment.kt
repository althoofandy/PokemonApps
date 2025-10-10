package com.example.features.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.base.BaseFragment
import com.example.features.R
import com.example.features.adapter.PokemonListAdapter
import com.example.features.adapter.PokemonLoadStateAdapter
import com.example.features.databinding.FragmentPokemonListBinding
import com.example.features.ui.detail.PokemonDetailFragment
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
        val layoutManager = context?.let { ctx ->
            GridLayoutManager(ctx, 2)
        }
        layoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapterItemCount = adapter.itemCount
                return if (position >= adapterItemCount) layoutManager?.spanCount ?: 0 else 1
            }
        }

        binding.recyclerViewPokemon.layoutManager = layoutManager
        binding.recyclerViewPokemon.adapter = adapter.withLoadStateFooter(
            footer = PokemonLoadStateAdapter { adapter.retry() }
        )

        viewModel.pokemonList.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

        binding.tieSearch.addTextChangedListener { text ->
            viewModel.setQuery(text.toString())
            adapter.refresh()
        }
    }

}
