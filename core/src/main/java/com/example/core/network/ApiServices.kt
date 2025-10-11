package com.example.core.network

import com.example.core.model.PokemonDetailResponse
import com.example.core.model.PokemonListResponse
import com.example.core.model.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDetailResponse

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(
        @Path("name") name: String
    ): PokemonSpeciesResponse

}