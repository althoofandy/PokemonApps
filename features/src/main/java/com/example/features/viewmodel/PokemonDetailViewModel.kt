package com.example.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.CoroutinesDispatcherProvider
import com.example.core.model.PokemonDetailUIModel
import com.example.core.utils.UiState
import com.example.data.local.PokemonFavoriteEntity
import com.example.data.repository.PokemonLocalRepository
import com.example.data.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailViewModel(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val localRepo: PokemonLocalRepository,
    private val dispatcher: CoroutinesDispatcherProvider
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

        val response = withContext(dispatcher.io) {
            getPokemonDetailUseCase(name)
        }
        if (response is UiState.Success) {
            val favorite = withContext(dispatcher.io) { localRepo.isFavorite(response.data.id) }
            _pokemonDetail.value = UiState.Success(response.data.copy(isFavorite = favorite))
        } else if (response is UiState.Error) {
            _pokemonDetail.value = UiState.Error(response.message)
        }
    }
}

