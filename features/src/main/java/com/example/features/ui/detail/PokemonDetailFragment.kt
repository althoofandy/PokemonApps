package com.example.features.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.core.base.BaseFragment
import com.example.core.model.PokemonDetailUIModel
import com.example.core.utils.Constant.POKENAME_ARGS
import com.example.core.utils.isLoading
import com.example.core.utils.onError
import com.example.core.utils.onSuccess
import com.example.data.local.PokemonFavoriteEntity
import com.example.features.adapter.PokemonDetailPagerAdapter
import com.example.features.databinding.FragmentPokemonDetailBinding
import com.example.features.viewmodel.PokemonDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailFragment :
    BaseFragment<FragmentPokemonDetailBinding>() {

    private val viewModel: PokemonDetailViewModel by viewModel()
    private val pokeName by lazy { arguments?.getString(POKENAME_ARGS).orEmpty() }
    private var currentPokemonDetail: PokemonFavoriteEntity? = null
    private var isFavorite = false
    private var isPagerInitialized = false

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
                if (!isPagerInitialized) {
                    setupViewPager(it)
                    isPagerInitialized = true
                }
                updateUI(it)
            }
            state.onError {
                showToast("Error")
            }
        }
    }

    private fun setupViewPager(pokemon: PokemonDetailUIModel) = with(binding) {
        val adapter = PokemonDetailPagerAdapter(this@PokemonDetailFragment, pokemon)
        layoutDetailpokemon.viewPager.adapter = adapter
        layoutDetailpokemon.viewPager.offscreenPageLimit = 3
        TabLayoutMediator(
            layoutDetailpokemon.tabLayout,
            layoutDetailpokemon.viewPager
        ) { tab, pos ->
            tab.text = listOf("About", "Base Stats", "Evolution")[pos]
        }.attach()
    }

    private fun updateUI(pokemon: PokemonDetailUIModel) = with(binding) {
        setFavorite(pokemon)
        layoutDetailpokemon.cvPagerPokemon.isVisible = true
        layoutDetailpokemon.tvName.text = pokemon.name
        layoutDetailpokemon.tvNumber.text = "#${pokemon.id}"
        layoutDetailpokemon.tvTypes.text = pokemon.types.joinToString(" • ")
        Glide.with(layoutDetailpokemon.ivPokemon).load(pokemon.imageUrl)
            .into(layoutDetailpokemon.ivPokemon)
        layoutDetailpokemon.llDetailPokemon.setBackgroundColor(pokemon.color)
        requireActivity().setStatusBarByColor(pokemon.color)
    }

    private fun setFavorite(data: PokemonDetailUIModel) {
        binding.layoutDetailpokemon.btnFavorite.isVisible = true
        isFavorite = data.isFavorite
        currentPokemonDetail = PokemonFavoriteEntity(
            id = data.id,
            name = data.name,
            imageUrl = data.imageUrl,
            isFavorite = data.isFavorite
        )
        binding.layoutDetailpokemon.btnFavorite.isSelected = data.isFavorite
        binding.layoutDetailpokemon.btnFavorite.setOnClickListener {
            currentPokemonDetail?.let { viewModel.toggleFavorite(it) }
        }
    }

    override fun fetchData() {
        viewModel.getPokemonDetail(pokeName)
    }

    override fun onResume() {
        super.onResume()
        isPagerInitialized = false
    }
}
