package hr.from.ivantoplak.pokemonapp.repository

import hr.from.ivantoplak.pokemonapp.model.Move
import hr.from.ivantoplak.pokemonapp.model.Pokemon
import hr.from.ivantoplak.pokemonapp.model.Stat
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonNames(): List<String>

    suspend fun getPokemon(name: String): Pokemon?

    suspend fun getPokemonMoves(pokemonId: Int): Flow<List<Move>>

    suspend fun getPokemonStats(pokemonId: Int): Flow<List<Stat>>
}
