package com.example.features.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.utils.Navigator
import com.example.features.adapter.PokemonListAdapter
import com.example.features.adapter.PokemonLoadStateAdapter
import com.example.features.databinding.FragmentPokemonListBinding
import com.example.features.viewmodel.PokemonListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : BaseFragment<FragmentPokemonListBinding>() {

    private val viewModel: PokemonListViewModel by viewModel()
    private var searchJob: Job? = null

    private val adapter by lazy {
        PokemonListAdapter().apply {
            setOnItemClickListener { pokemon ->
                (requireActivity() as? Navigator)?.toPokemonDetail(pokemon.name)
            }
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPokemonListBinding.inflate(inflater, container, false)

    private fun setRecyclerView() = binding.apply {
        val layoutManager = context?.let { ctx ->
            GridLayoutManager(ctx, 2)
        }
        layoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapterItemCount = adapter.itemCount
                return if (position >= adapterItemCount) layoutManager?.spanCount ?: 0 else 1
            }
        }

        recyclerViewPokemon.layoutManager = layoutManager
        recyclerViewPokemon.adapter = adapter.withLoadStateFooter(
            footer = PokemonLoadStateAdapter { adapter.retry() }
        )
    }

    override fun observeData() {
        super.observeData()
        setRecyclerView()
        viewModel.pokemonPagingData.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

        binding.tieSearch.addTextChangedListener { text ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(200)
                viewModel.searchPokemon(text.toString())
            }
        }
    }
}
