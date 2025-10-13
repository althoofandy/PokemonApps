package com.example.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.core.model.PokemonListUiModel
import com.example.data.usecase.GetPokemonListUseCase

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val queryLiveData = MutableLiveData("")

    val pokemonPagingData: LiveData<PagingData<PokemonListUiModel>> =
        queryLiveData.switchMap { query ->
            getPokemonListUseCase(query)
                .liveData
        }.cachedIn(viewModelScope)

    fun searchPokemon(query: String) {
        queryLiveData.value = query
    }
}

