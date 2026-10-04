package com.example.feature.pokedex

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.ui.base.BaseFragment
import com.example.core.ui.navigation.Navigator
import com.example.feature.pokedex.adapter.PokemonListAdapter
import com.example.feature.pokedex.adapter.PokemonLoadStateAdapter
import com.example.feature.pokedex.databinding.FragmentPokemonListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : BaseFragment<FragmentPokemonListBinding>() {

    private val viewModel: PokemonListViewModel by viewModel()

    private val adapter by lazy {
        PokemonListAdapter { pokemon ->
            (requireActivity() as? Navigator)?.toPokemonDetail(pokemon.name)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPokemonListBinding.inflate(inflater, container, false)

    private fun setRecyclerView() = with(binding) {
        val layoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == adapter.itemCount && adapter.itemCount > 0) 2 else 1
                }
            }
        }

        recyclerViewPokemon.layoutManager = layoutManager
        recyclerViewPokemon.adapter = adapter.withLoadStateFooter(
            footer = PokemonLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading
            val isEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showLoading(isLoading)
            showEmpty(isEmpty)
        }

        recyclerViewPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                btnScrollToTop.visibility = if (firstVisible > 5) View.VISIBLE else View.GONE
            }
        })

        btnScrollToTop.setOnClickListener {
            recyclerViewPokemon.smoothScrollToPosition(0)
        }
    }

    override fun observeData() {
        super.observeData()
        setRecyclerView()
        viewModel.pokemonPagingData.collectWithLifecycle(adapter::submitData)

        binding.tieSearch.addTextChangedListener { text ->
            viewModel.searchPokemon(text.toString())
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

    override fun onResume() {
        super.onResume()
        requireActivity().setStatusBarByColor(Color.WHITE)
    }
}
