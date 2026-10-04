package com.example.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.GetFavoritesUseCase
import com.example.core.model.FavoritePokemon

class FavoritePokemonViewModel(
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val favorites: LiveData<List<FavoritePokemon>> = getFavoritesUseCase().asLiveData()
}
