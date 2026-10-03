package com.example.data.usecase

import com.example.core.model.EvolutionUIModel
import com.example.core.model.PokemonChainResponse
import com.example.core.model.PokemonDetailUIModel
import com.example.core.utils.UiState
import com.example.core.utils.flatMap
import com.example.core.utils.processResponse
import com.example.data.mapper.toFlavorText
import com.example.data.mapper.toUIModel
import com.example.data.repository.PokemonRepository

class PokemonDetailUseCaseImpl(
    private val repository: PokemonRepository
) : GetPokemonDetailUseCase {

    override suspend fun invoke(name: String): UiState<PokemonDetailUIModel> {
        val detailState = processResponse(repository.getPokemonDetail(name)) { it }

        return detailState.flatMap { detail ->
            val speciesName = detail.species?.name ?: name
            val speciesState = processResponse(repository.getPokemonSpecies(speciesName)) { it }

            speciesState.flatMap { species ->
                val flavorText = species.toFlavorText()
                val colorSpecies = species.color?.name
                val speciesUrl = species.evolutionChain?.url.orEmpty()

                val evolutionList =
                    processResponse(repository.getPokemonEvolutionChain(speciesUrl)) {
                        parseEvolutionChain(it)
                    }
                evolutionList.flatMap { evolution ->
                    UiState.Success(
                        detail.toUIModel(
                            flavorText = flavorText,
                            color = colorSpecies,
                            evolutionList = evolution
                        )
                    )
                }
            }
        }
    }

    private fun parseEvolutionChain(chain: PokemonChainResponse): List<EvolutionUIModel> {
        val result = mutableListOf<EvolutionUIModel>()

        fun traverse(node: PokemonChainResponse.Chain) {
            val name = node.species.name
            val id = node.species.url.trimEnd('/').split("/").last()
            val imageUrl = repository.getPokemonImageUrl(id.toInt())
            val level = node.evolutionDetails?.firstOrNull()?.minLevel?.toString()
            result.add(EvolutionUIModel(name, imageUrl, level))
            node.evolvesTo.forEach { traverse(it) }
        }

        traverse(chain.chain)
        return result
    }
}

