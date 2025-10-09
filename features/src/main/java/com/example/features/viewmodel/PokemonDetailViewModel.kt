package com.example.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.PokemonDetailUIModel
import com.example.core.utils.UiState
import com.example.data.local.PokemonFavoriteEntity
import com.example.data.repository.PokemonLocalRepository
import com.example.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val repository: PokemonRepository,
    private val localRepo: PokemonLocalRepository
) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<UiState<PokemonDetailUIModel>>()
    val pokemonDetail: LiveData<UiState<PokemonDetailUIModel>> = _pokemonDetail

    fun toggleFavorite(pokemon: PokemonFavoriteEntity) = viewModelScope.launch {
        localRepo.updateFavorite(pokemon)
        val currentDetail = _pokemonDetail.value
        if (currentDetail is UiState.Success) {
            val updatedDetail = currentDetail.data.copy(isFavorite = !currentDetail.data.isFavorite)
            _pokemonDetail.value = UiState.Success(updatedDetail)
        }
    }

    fun getPokemonDetail(name: String) = viewModelScope.launch {
        _pokemonDetail.value = UiState.Loading
        when (val response = repository.getPokemonDetail(name)) {
            is UiState.Success -> {
                val detail = response.data
                val favorite = localRepo.isFavorite(detail.id)
                _pokemonDetail.value = UiState.Success(
                    detail.copy(isFavorite = favorite)
                )
            }

            is UiState.Error -> _pokemonDetail.value = UiState.Error(response.message)
            UiState.Loading -> _pokemonDetail.value = UiState.Loading
           else -> {}
        }

    }
}

