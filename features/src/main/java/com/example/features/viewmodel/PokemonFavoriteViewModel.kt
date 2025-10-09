package com.example.features.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import com.example.data.local.PokemonFavoriteEntity
import com.example.data.repository.PokemonLocalRepository
import kotlinx.coroutines.launch

class FavoritePokemonViewModel(
    private val localRepo: PokemonLocalRepository
) : ViewModel() {

    val favorites: LiveData<List<PokemonFavoriteEntity>> = localRepo.getFavorites()

    fun addFavorite(pokemon: PokemonFavoriteEntity) {
        viewModelScope.launch {
            localRepo.addFavorite(pokemon)
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            localRepo.removeFavorite(id)
        }
    }
}
