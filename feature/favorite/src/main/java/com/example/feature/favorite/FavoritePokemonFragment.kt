package com.example.feature.favorite

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.base.BaseFragment
import com.example.core.ui.navigation.Navigator
import com.example.core.ui.state.UiState
import com.example.feature.favorite.adapter.FavoritePokemonAdapter
import com.example.feature.favorite.databinding.FragmentPokemonFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritePokemonFragment : BaseFragment<FragmentPokemonFavoriteBinding>() {

    private val viewModel: FavoritePokemonViewModel by viewModel()
    private val adapter by lazy {
        FavoritePokemonAdapter { pokemon ->
            (requireActivity() as? Navigator)?.toPokemonDetail(pokemon.name)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonFavoriteBinding.inflate(inflater, container, false)

    override fun observeData() {
        super.observeData()
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorites.adapter = adapter
        viewModel.favorites.collectWithLifecycle { state ->
            when (state) {
                is UiState.Success -> {
                    adapter.submitList(state.data)
                    showEmpty(false)
                }
                UiState.Empty -> {
                    adapter.submitList(emptyList())
                    showEmpty(true)
                }
                else -> Unit
            }
        }
    }

    private fun showEmpty(state: Boolean) = binding.apply {
        recyclerViewFavorites.visibility = if (state) View.GONE else View.VISIBLE
        tvEmpty.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        requireActivity().setStatusBarByColor(Color.WHITE)
    }
}
