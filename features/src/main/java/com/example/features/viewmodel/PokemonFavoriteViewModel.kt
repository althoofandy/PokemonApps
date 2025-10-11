package com.example.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.PokemonFavoriteEntity
import com.example.data.repository.PokemonLocalRepository
import kotlinx.coroutines.launch

class FavoritePokemonViewModel(
    private val localRepo: PokemonLocalRepository
) : ViewModel() {

    val favorites: LiveData<List<PokemonFavoriteEntity>> = localRepo.getFavorites()

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            localRepo.removeFavorite(id)
        }
    }
}
