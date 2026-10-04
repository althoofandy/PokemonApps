package com.example.feature.detail.tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ui.base.BaseFragment
import com.example.core.ui.state.onSuccess
import com.example.feature.detail.PokemonDetailViewModel
import com.example.feature.detail.adapter.EvolutionAdapter
import com.example.feature.detail.databinding.FragmentPokemonEvolutionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonEvolutionFragment : BaseFragment<FragmentPokemonEvolutionBinding>() {

    private val viewModel: PokemonDetailViewModel by viewModel(
        ownerProducer = { requireParentFragment() }
    )

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPokemonEvolutionBinding.inflate(inflater, container, false)

    override fun observeData() {
        binding.recyclerEvolution.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewModel.pokemonDetail.observe(viewLifecycleOwner) { state ->
            state.onSuccess { binding.recyclerEvolution.adapter = EvolutionAdapter(it.evolutions) }
        }
    }
}
