package com.example.features.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.core.base.BaseFragment
import com.example.core.model.PokemonDetailUIModel
import com.example.core.utils.isLoading
import com.example.core.utils.onSuccess
import com.example.data.local.PokemonFavoriteEntity
import com.example.features.R
import com.example.features.databinding.FragmentPokemonDetailBinding
import com.example.features.viewmodel.PokemonDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailFragment :
    BaseFragment<FragmentPokemonDetailBinding>() {

    private val viewModel: PokemonDetailViewModel by viewModel()
    private val pokeName by lazy { arguments?.getString(ARG_POKE_NAME).orEmpty() }
    private var currentPokemonDetail: PokemonFavoriteEntity? = null
    private var isFavorite = false
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPokemonDetailBinding {
        return FragmentPokemonDetailBinding.inflate(inflater, container, false)
    }

    override fun observeData() {
        viewModel.pokemonDetail.observe(viewLifecycleOwner) { state ->
            showLoading(binding.progressBar, state.isLoading)
            state.onSuccess {
                initSuccess(it)
            }
        }
    }

    private fun initSuccess(data: PokemonDetailUIModel) = binding.apply {
        isFavorite = data.isFavorite
        currentPokemonDetail = PokemonFavoriteEntity(
            id = data.id,
            name = data.name,
            imageUrl = data.imageUrl,
            isFavorite = data.isFavorite
        )
        btnFavorite.visibility = View.VISIBLE
        btnFavorite.setColorFilter(if (data.isFavorite) Color.RED else Color.GRAY)
        context?.let { ctx ->
            Glide.with(ctx)
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivPokemonDetail)
        }

        tvPokemonNameDetail.text = data.name
        tvPokemonHeight.text = "Height: ${data.height}"
        tvPokemonWeight.text = "Weight: ${data.weight}"
        tvPokemonTypes.text = "Types: ${data.types.joinToString(", ")}"
        tvPokemonAbilities.text = "Abilities: ${data.abilities.joinToString(", ")}"
        btnFavorite.setOnClickListener {
            if (currentPokemonDetail != null) {
                viewModel.toggleFavorite(currentPokemonDetail!!)
            }
        }
    }

    override fun fetchData() {
        viewModel.getPokemonDetail(pokeName)
    }

    companion object {
        private const val ARG_POKE_NAME = "pokeName"

        @JvmStatic
        fun newInstance(name: String) =
            PokemonDetailFragment().apply {
                arguments = Bundle().apply { putString(ARG_POKE_NAME, name) }
            }
    }
}
