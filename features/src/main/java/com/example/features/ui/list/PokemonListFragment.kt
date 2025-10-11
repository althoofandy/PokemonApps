package com.example.features.ui.list

import android.view.LayoutInflater
import android.view.View
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
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount && adapter.itemCount > 0) 2 else 1
            }
        }

        recyclerViewPokemon.layoutManager = layoutManager
        recyclerViewPokemon.adapter = adapter.withLoadStateFooter(
            footer = PokemonLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is androidx.paging.LoadState.Loading
            val isEmpty = loadState.refresh is androidx.paging.LoadState.NotLoading &&
                    adapter.itemCount == 0
            showLoading(isLoading)
            showEmpty(isEmpty)
        }
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

    private fun showEmpty(state: Boolean) = binding.apply {
        recyclerViewPokemon.visibility = if (state) View.GONE else View.VISIBLE
        layoutEmptyState.layoutEmptyState.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) = binding.apply {
        recyclerViewPokemon.visibility = if (state) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
