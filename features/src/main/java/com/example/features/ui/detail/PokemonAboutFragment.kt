package com.example.features.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.core.model.PokemonDetail
import com.example.core.ui.base.BaseFragment
import com.example.core.ui.state.onSuccess
import com.example.features.R
import com.example.features.databinding.FragmentPokemonAboutBinding
import com.example.features.viewmodel.PokemonDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonAboutFragment : BaseFragment<FragmentPokemonAboutBinding>() {

    private val viewModel: PokemonDetailViewModel by viewModel(
        ownerProducer = { requireParentFragment() }
    )

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonAboutBinding.inflate(inflater, container, false)

    override fun observeData() {
        viewModel.pokemonDetail.observe(viewLifecycleOwner) { state ->
            state.onSuccess(::setupUI)
        }
    }

    private fun setupUI(pokemon: PokemonDetail) = with(binding) {
        tvDescription.text =
            pokemon.description.ifBlank { getString(R.string.no_description_available) }
        val infoList = listOf(
            getString(R.string.height) to "${pokemon.heightInMeters} m",
            getString(R.string.weight) to "${pokemon.weightInKg} kg",
            "Abilities" to pokemon.abilities.joinToString(", ")
        )

        containerInfo.removeAllViews()
        infoList.forEach { (title, value) ->
            val itemView = layoutInflater
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
}
