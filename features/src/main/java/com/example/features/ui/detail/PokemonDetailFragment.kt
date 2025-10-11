package com.example.features.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.core.base.BaseFragment
import com.example.core.model.PokemonDetailUIModel
import com.example.core.utils.Constant.POKENAME_ARGS
import com.example.core.utils.PokemonSpeciesColor
import com.example.core.utils.PokemonType
import com.example.core.utils.isLoading
import com.example.core.utils.onError
import com.example.core.utils.onSuccess
import com.example.data.local.PokemonFavoriteEntity
import com.example.features.R
import com.example.features.databinding.FragmentPokemonDetailBinding
import com.example.features.databinding.ItemStatRowBinding
import com.example.features.viewmodel.PokemonDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailFragment :
    BaseFragment<FragmentPokemonDetailBinding>() {

    private val viewModel: PokemonDetailViewModel by viewModel()
    private val pokeName by lazy { arguments?.getString(POKENAME_ARGS).orEmpty() }
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
            state.onError {
                showToast("Error")
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

        llDetailPokemon.visibility = View.VISIBLE
        btnFavorite.setColorFilter(if (data.isFavorite) Color.RED else Color.GRAY)

        Glide.with(ivPokemonDetail)
            .load(data.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(ivPokemonDetail)

        tvPokemonNameDetail.text = data.name.capitalize()
        tvPokemonHeight.text = "Height: ${data.height}"
        tvPokemonWeight.text = "Weight: ${data.weight}"
        tvPokemonDescription.text = data.description

        containerTypes.removeAllViews()
        data.types.forEach { type ->
            val chip = TextView(requireContext()).apply {
                text = type.replaceFirstChar { it.uppercase() }
                setPadding(20, 10, 20, 10)
                setTextColor(Color.WHITE)
                textSize = 14f
                background = resources.getDrawable(R.drawable.bg_type_chip)
            }
            val chipColor = PokemonType.fromString(type).color
            containerTypes.addView(chip)
            chip.background.setTint(chipColor)
        }
        containerStats.removeAllViews()
        data.stats.forEach { (name, value) ->
            val speciesColor = PokemonSpeciesColor.fromString(data.color).color
            val itemStat = ItemStatRowBinding.inflate(layoutInflater, containerStats, false)
            itemStat.tvStatName.text = name.uppercase()
            itemStat.pbStat.progress = value
            itemStat.pbStat.progressTintList = ColorStateList.valueOf(speciesColor)
            itemStat.tvStatValue.text = value.toString()
            containerStats.addView(itemStat.root)
        }

        btnFavorite.setOnClickListener {
            currentPokemonDetail?.let { viewModel.toggleFavorite(it) }
        }
    }

    override fun fetchData() {
        viewModel.getPokemonDetail(pokeName)
    }
}
