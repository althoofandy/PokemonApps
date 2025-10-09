package com.example.features.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.data.repository.PokemonRepository
import kotlinx.coroutines.flow.map

class PokemonListViewModel(
    pokemonRepository: PokemonRepository
) : ViewModel() {

    val pokemonPagingData = pokemonRepository.getPokemonPager()
        .flow
        .map { pagingData ->
            pagingData.map { it }
        }
        .cachedIn(viewModelScope)
        .asLiveData()
}
