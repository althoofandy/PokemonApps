package com.example.core.data.fake

import com.example.core.data.network.ApiService
import com.example.core.data.network.model.PokemonChainResponse
import com.example.core.data.network.model.PokemonDetailResponse
import com.example.core.data.network.model.PokemonListResponse
import com.example.core.data.network.model.PokemonResult
import com.example.core.data.network.model.PokemonSpeciesResponse

internal class FakeApiService(
    private val pokemonNames: List<String> = emptyList()
) : ApiService {

    val details = mutableMapOf<String, PokemonDetailResponse>()
    val species = mutableMapOf<String, PokemonSpeciesResponse>()
    val evolutionChains = mutableMapOf<String, PokemonChainResponse>()

    val listRequests = mutableListOf<Pair<Int, Int>>()
    val speciesRequests = mutableListOf<String>()
    val evolutionChainRequests = mutableListOf<String>()
    var failure: Exception? = null

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        failure?.let { throw it }
        listRequests += limit to offset
        val page = pokemonNames.withIndex().drop(offset).take(limit).map { (index, name) ->
            PokemonResult(name = name, url = "https://pokeapi.co/api/v2/pokemon/${index + 1}/")
        }
        return PokemonListResponse(results = page)
    }

    override suspend fun getPokemonDetail(name: String): PokemonDetailResponse {
        failure?.let { throw it }
        return details[name] ?: throw NoSuchElementException("pokemon/$name")
    }

    override suspend fun getPokemonSpecies(name: String): PokemonSpeciesResponse {
        failure?.let { throw it }
        speciesRequests += name
        return species[name] ?: throw NoSuchElementException("pokemon-species/$name")
    }

    override suspend fun getPokemonEvolutionChain(url: String): PokemonChainResponse {
        failure?.let { throw it }
        evolutionChainRequests += url
        return evolutionChains[url] ?: throw NoSuchElementException(url)
    }
}
