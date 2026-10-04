package com.example.core.data.mapper

import com.example.core.data.local.PokemonFavoriteEntity
import com.example.core.model.FavoritePokemon

internal fun PokemonFavoriteEntity.toFavoritePokemon(): FavoritePokemon =
    FavoritePokemon(id = id, name = name, imageUrl = imageUrl)

internal fun FavoritePokemon.toEntity(): PokemonFavoriteEntity =
    PokemonFavoriteEntity(id = id, name = name, imageUrl = imageUrl, isFavorite = true)
