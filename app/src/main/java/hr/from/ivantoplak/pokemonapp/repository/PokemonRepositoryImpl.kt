package hr.from.ivantoplak.pokemonapp.repository

import hr.from.ivantoplak.pokemonapp.mappings.toPokemon
import hr.from.ivantoplak.pokemonapp.model.Pokemon
import hr.from.ivantoplak.pokemonapp.service.PokemonService

class PokemonRepositoryImpl(private val pokemonService: PokemonService) : PokemonRepository {

    override suspend fun getPokemonNames(): List<String> =
        pokemonService.getPokemonNames(Int.MAX_VALUE, 0).results.map { it.name }

    override suspend fun getPokemon(name: String): Pokemon =
        pokemonService.getPokemon(name).toPokemon()
}