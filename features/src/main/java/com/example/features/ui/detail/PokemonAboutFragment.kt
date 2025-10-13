package com.example.features.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.core.base.BaseFragment
import com.example.core.model.PokemonDetailUIModel
import com.example.features.R
import com.example.features.databinding.FragmentPokemonAboutBinding


class PokemonAboutFragment : BaseFragment<FragmentPokemonAboutBinding>() {

    private val pokemonData by lazy {
        arguments?.getParcelable<PokemonDetailUIModel>(ARG_PARAM1)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonAboutBinding.inflate(inflater, container, false)

    override fun observeData() {
        super.observeData()
        setupUI()
    }

    private fun setupUI() = with(binding) {
        tvDescription.text =
            pokemonData?.description?.ifBlank { getString(R.string.no_description_available) }
        val infoList = listOf(
            "Height" to pokemonData?.height,
            "Weight" to pokemonData?.weight,
            "Abilities" to pokemonData?.abilities?.joinToString(", ")
        )

        infoList.forEach { (title, value) ->
            val itemView = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_2, containerInfo, false)
            itemView.findViewById<TextView>(android.R.id.text1).apply {
                text = title
                textSize = 14f
            }
            itemView.findViewById<TextView>(android.R.id.text2).apply {
                text = value
                textSize = 16f
            }
            containerInfo.addView(itemView)
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"

        @JvmStatic
        fun newInstance(param1: PokemonDetailUIModel?) =
            PokemonAboutFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}