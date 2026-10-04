package com.example.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.core.model.PokemonDetail
import com.example.core.ui.base.BaseFragment
import com.example.core.ui.navigation.Constant.POKENAME_ARGS
import com.example.core.ui.state.isLoading
import com.example.core.ui.state.onError
import com.example.core.ui.state.onSuccess
import com.example.core.ui.utils.PokemonSpeciesColor
import com.example.feature.detail.adapter.PokemonDetailPagerAdapter
import com.example.feature.detail.databinding.FragmentPokemonDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailFragment : BaseFragment<FragmentPokemonDetailBinding>() {

    private val viewModel: PokemonDetailViewModel by viewModel()
    private val pokeName by lazy { arguments?.getString(POKENAME_ARGS).orEmpty() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        binding.layoutDetailpokemon.btnFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    override fun observeData() {
        viewModel.pokemonDetail.collectWithLifecycle { state ->
            showLoading(binding.progressBar, state.isLoading)
            state.onSuccess(::updateUI)
            state.onError {
                showToast("Error")
                viewModel.onErrorShown()
            }
        }
    }

    override fun fetchData() {
        viewModel.getPokemonDetail(pokeName)
    }

    private fun setupViewPager() = with(binding.layoutDetailpokemon) {
        viewPager.adapter = PokemonDetailPagerAdapter(this@PokemonDetailFragment)
        viewPager.offscreenPageLimit = 3
        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = listOf("About", "Base Stats", "Evolution")[pos]
        }.attach()
    }

    private fun updateUI(pokemon: PokemonDetail) = with(binding.layoutDetailpokemon) {
        val color = PokemonSpeciesColor.fromString(pokemon.speciesColor).color
        btnFavorite.isVisible = true
        btnFavorite.isSelected = pokemon.isFavorite
        cvPagerPokemon.isVisible = true
        tvName.text = pokemon.name
        tvNumber.text = "#${pokemon.id}"
        tvTypes.text = pokemon.types.joinToString(" • ")
        Glide.with(ivPokemon).load(pokemon.imageUrl).into(ivPokemon)
        llDetailPokemon.setBackgroundColor(color)
        requireActivity().setStatusBarByColor(color)
    }
}
