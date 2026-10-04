package com.example.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetPokemonDetailUseCase
import com.example.core.domain.usecase.ToggleFavoriteUseCase
import com.example.core.model.PokemonDetail
import com.example.core.ui.state.UiState
import com.example.core.ui.state.toUiState
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<UiState<PokemonDetail>>()
    val pokemonDetail: LiveData<UiState<PokemonDetail>> = _pokemonDetail

    fun getPokemonDetail(name: String) = viewModelScope.launch {
        _pokemonDetail.value = UiState.Loading
        _pokemonDetail.value = getPokemonDetailUseCase(name).toUiState()
    }

    fun toggleFavorite() = viewModelScope.launch {
        val pokemon = (_pokemonDetail.value as? UiState.Success)?.data ?: return@launch
        val isFavorite = toggleFavoriteUseCase(pokemon)
        _pokemonDetail.value = UiState.Success(pokemon.copy(isFavorite = isFavorite))
    }
}
