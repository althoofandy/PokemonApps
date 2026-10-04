package com.example.feature.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetFavoritesUseCase
import com.example.core.model.FavoritePokemon
import com.example.core.ui.state.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritePokemonViewModel(
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val favorites: StateFlow<UiState<List<FavoritePokemon>>> = getFavoritesUseCase()
        .map { favorites -> if (favorites.isEmpty()) UiState.Empty else UiState.Success(favorites) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = UiState.Loading
        )

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
