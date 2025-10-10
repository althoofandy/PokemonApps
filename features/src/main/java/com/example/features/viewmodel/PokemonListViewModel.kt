package com.example.features.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.data.repository.PokemonRepository

class PokemonListViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val searchQuery = MutableLiveData("")

    val pokemonList = searchQuery.switchMap { query ->
        pokemonRepository.getPokemonList(query)
            .liveData
    }.cachedIn(viewModelScope)

    fun setQuery(query: String?) {
        if (searchQuery.value != query) {
            searchQuery.value = query
        }
    }
}

