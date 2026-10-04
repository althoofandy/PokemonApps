package com.example.feature.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.domain.usecase.GetPokemonListUseCase
import com.example.core.model.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val query = MutableStateFlow("")

    val pokemonPagingData: Flow<PagingData<Pokemon>> = query
        .debounce(SEARCH_DEBOUNCE_MILLIS)
        .flatMapLatest { getPokemonListUseCase(it) }
        .cachedIn(viewModelScope)

    fun searchPokemon(query: String) {
        this.query.value = query
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 200L
    }
}
