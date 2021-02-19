package hr.from.ivantoplak.pokemonapp.repository

import kotlinx.coroutines.flow.distinctUntilChanged
import android.util.Log
import hr.from.ivantoplak.pokemonapp.coroutines.CoroutineContextProvider
import hr.from.ivantoplak.pokemonapp.db.dao.PokemonDao
import hr.from.ivantoplak.pokemonapp.mappings.toMoves
import hr.from.ivantoplak.pokemonapp.mappings.toPokemon
import hr.from.ivantoplak.pokemonapp.mappings.toStats
import hr.from.ivantoplak.pokemonapp.model.Move
import hr.from.ivantoplak.pokemonapp.model.Pokemon
import hr.from.ivantoplak.pokemonapp.model.Stat
import hr.from.ivantoplak.pokemonapp.service.PokemonService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonRepositoryImpl(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    private val dispatcher: CoroutineContextProvider
) : PokemonRepository {

    override suspend fun getPokemonNames(): List<String> = withContext(dispatcher.io()) {
        pokemonService.getPokemonNames(Int.MAX_VALUE, 0).results.map { it.name }
    }

    override suspend fun getPokemon(name: String): Pokemon? = withContext(dispatcher.io()) {
        // try to get pokemon from the remote API
        var pokemon: Pokemon?
        try {
            pokemon = refreshPokemon(name)
        } catch (ex: Exception) {
            // get random pokemon from db
            pokemon = getRandomPokemon()
            // log exception TODO
            Log.e("", "")
        }
        pokemon
    }

    override suspend fun getPokemonMoves(pokemonId: Int): Flow<List<Move>> =
        withContext(dispatcher.io()) {
            pokemonDao.getMoves(pokemonId).distinctUntilChanged().map { it.toMoves() }
        }

    override suspend fun getPokemonStats(pokemonId: Int): Flow<List<Stat>> =
        withContext(dispatcher.io()) {
            pokemonDao.getStats(pokemonId).distinctUntilChanged().map { it.toStats() }
        }

    /**
     * Get pokemon by [name] from the remote API and save it to the local db.
     */
    private suspend fun refreshPokemon(name: String): Pokemon {
        val pokemon = pokemonService.getPokemon(name)
        return pokemonDao.savePokemonData(pokemon)
    }

    private suspend fun getRandomPokemon(): Pokemon? {
        // get list of ID-s from db
        val ids = pokemonDao.getPokemonIds()
        if (ids.isEmpty()) return null
        val randIndex = (0..ids.lastIndex).random()
        val randId = ids[randIndex].toInt()
        // get pokemon from db with random ID
        return pokemonDao.getPokemonById(randId)?.toPokemon()
    }
}

