package hr.from.ivantoplak.pokemonapp.repository

import hr.from.ivantoplak.pokemonapp.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.db.dao.PokemonDao
import hr.from.ivantoplak.pokemonapp.db.model.DbPokemonName
import hr.from.ivantoplak.pokemonapp.mappings.toMoves
import hr.from.ivantoplak.pokemonapp.mappings.toPokemon
import hr.from.ivantoplak.pokemonapp.mappings.toStats
import hr.from.ivantoplak.pokemonapp.model.Move
import hr.from.ivantoplak.pokemonapp.model.Pokemon
import hr.from.ivantoplak.pokemonapp.model.Stat
import hr.from.ivantoplak.pokemonapp.service.PokemonService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val ErrorGetPokemonNames = "Error while retrieving and saving pokemon names."
private const val ErrorGetPokemon = "Error while retrieving and saving pokemon."

class PokemonRepositoryImpl(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    private val dispatcher: DispatcherProvider,
) : PokemonRepository {

    /**
     * Get pokemon names from the remote API and save them to the local db.
     * Return the list of names from local db.
     */
    override suspend fun getPokemonNames(): List<String> = withContext(dispatcher.io()) {
        val pokemonNames = mutableListOf<String>()
        try {
            val dbPokemonNames = pokemonService.getPokemonNames(
                Int.MAX_VALUE,
                0,
            ).results.map { DbPokemonName(name = it.name) }
            pokemonDao.insertPokemonNames(dbPokemonNames)
        } catch (ex: Exception) {
            Timber.e(ex, ErrorGetPokemonNames)
        } finally {
            pokemonNames.addAll(pokemonDao.getPokemonNames().map { it.name })
        }
        pokemonNames
    }

    /**
     * Get pokemon by [name] from the remote API and save it to the local db.
     * If it was successful, return it, otherwise get random pokemon from the local db.
     */
    override suspend fun getPokemon(name: String): Pokemon? = withContext(dispatcher.io()) {
        var pokemon: Pokemon?
        try {
            pokemon = if (name.isNotBlank()) refreshPokemon(name) else getRandomPokemon()
        } catch (ex: Exception) {
            pokemon = getRandomPokemon()
            Timber.e(ex, ErrorGetPokemon)
        }
        pokemon
    }

    override fun getPokemonMoves(pokemonId: Int): Flow<List<Move>> =
        pokemonDao.getMoves(pokemonId)
            .distinctUntilChanged()
            .map { it.toMoves() }
            .flowOn(dispatcher.io())

    override fun getPokemonStats(pokemonId: Int): Flow<List<Stat>> =
        pokemonDao.getStats(pokemonId)
            .distinctUntilChanged()
            .map { it.toStats() }
            .flowOn(dispatcher.io())

    /**
     * Get pokemon by [name] from the remote API and save it to the local db.
     */
    private suspend fun refreshPokemon(name: String): Pokemon {
        val pokemon = pokemonService.getPokemon(name)
        return pokemonDao.savePokemonData(pokemon)
    }

    /**
     * Get random pokemon from the local db.
     */
    private suspend fun getRandomPokemon(): Pokemon? {
        val ids = pokemonDao.getPokemonIds()
        if (ids.isEmpty()) return null
        return pokemonDao.getPokemonById(ids.random().toInt())?.toPokemon()
    }
}
