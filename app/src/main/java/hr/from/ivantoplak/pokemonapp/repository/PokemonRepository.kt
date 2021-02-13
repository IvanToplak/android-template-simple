package hr.from.ivantoplak.pokemonapp.repository

import hr.from.ivantoplak.pokemonapp.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemonNames(): List<String>

    suspend fun getPokemon(name: String): Pokemon
}
