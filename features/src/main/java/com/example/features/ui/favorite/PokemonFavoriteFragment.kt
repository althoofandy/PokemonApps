package com.example.features.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseFragment
import com.example.features.adapter.FavoritePokemonAdapter
import com.example.features.databinding.FragmentPokemonFavoriteBinding
import com.example.features.viewmodel.FavoritePokemonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritePokemonFragment : BaseFragment<FragmentPokemonFavoriteBinding>() {

    private val viewModel: FavoritePokemonViewModel by viewModel()
    private val adapter by lazy {
        FavoritePokemonAdapter()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonFavoriteBinding.inflate(inflater, container, false)

    override fun observeData() {
        super.observeData()
        context?.let {
            binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(it)
            binding.recyclerViewFavorites.adapter = adapter
        }
        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

}
