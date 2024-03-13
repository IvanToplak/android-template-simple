package hr.from.ivantoplak.pokemonapp.pokemon.model

import hr.from.ivantoplak.pokemonapp.common.coroutines.DispatcherProvider
import hr.from.ivantoplak.pokemonapp.pokemon.api.PokemonService
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbPokemonName
import hr.from.ivantoplak.pokemonapp.pokemon.db.PokemonDao
import hr.from.ivantoplak.pokemonapp.pokemon.mappings.toMoves
import hr.from.ivantoplak.pokemonapp.pokemon.mappings.toPokemon
import hr.from.ivantoplak.pokemonapp.pokemon.mappings.toStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val ErrorGetPokemonNames = "Error while retrieving and saving pokemon names."
private const val ErrorGetPokemon = "Error while retrieving and saving pokemon."

interface PokemonRepository {

    /**
     * Get pokemon names from the remote API and save them to the local db.
     * Return the list of names from local db.
     */
    suspend fun getPokemonNames(): List<String>

    /**
     * Get pokemon by [name] from the remote API and save it to the local db.
     * If it was successful, return it, otherwise get random pokemon from the local db.
     */
    suspend fun getPokemon(name: String): Pokemon?

    fun getPokemonMoves(pokemonId: Int): Flow<List<Move>>
    fun getPokemonStats(pokemonId: Int): Flow<List<Stat>>
}

class PokemonRepositoryImpl(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    private val dispatcher: DispatcherProvider,
) : PokemonRepository {

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
