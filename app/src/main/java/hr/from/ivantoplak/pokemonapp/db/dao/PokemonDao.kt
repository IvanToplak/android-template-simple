package hr.from.ivantoplak.pokemonapp.db.dao

import androidx.room.*
import hr.from.ivantoplak.pokemonapp.db.model.*
import hr.from.ivantoplak.pokemonapp.mappings.toDbMoves
import hr.from.ivantoplak.pokemonapp.mappings.toDbPokemon
import hr.from.ivantoplak.pokemonapp.mappings.toPokemon
import hr.from.ivantoplak.pokemonapp.model.Pokemon
import hr.from.ivantoplak.pokemonapp.service.model.ApiPokemon
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPokemon(pokemon: DbPokemon): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMoves(moves: List<DbMove>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertStats(stats: List<DbStat>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPokemonMoves(stats: List<DbPokemonMove>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPokemonStats(stats: List<DbPokemonStat>)

    @Query("SELECT id FROM pokemon")
    abstract suspend fun getPokemonIds(): List<Long>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    abstract suspend fun getPokemonById(id: Int): DbPokemon?

    @Query("SELECT m.* FROM pokemon_move pm JOIN move m ON m.id = pm.move_id WHERE pm.pokemon_id = :pokemonId")
    abstract fun getMoves(pokemonId: Int): Flow<List<DbMove>>

    @Query("SELECT s.id, s.name, ps.value FROM pokemon_stat ps JOIN stat s ON s.id = ps.stat_id WHERE ps.pokemon_id = :pokemonId")
    abstract fun getStats(pokemonId: Int): Flow<List<DbStatNameValue>>

    @Query("SELECT * FROM stat WHERE id = :id")
    abstract suspend fun getStatById(id: Int): DbStat?

    @Transaction
    open suspend fun savePokemonData(pokemon: ApiPokemon): Pokemon {
        val dbPokemon = pokemon.toDbPokemon()

        // save pokemon to db
        val pokemonId = insertPokemon(dbPokemon).toInt()

        // save moves to db
        val dbMoves = pokemon.toDbMoves()
        val movesIds = insertMoves(dbMoves).map { it.toInt() }
        val dbPokemonMoves =
            movesIds.map { moveId -> DbPokemonMove(moveId = moveId, pokemonId = pokemonId) }
        insertPokemonMoves(dbPokemonMoves)

        // save stats to db
        val statMap = mutableMapOf<String, Int>()
        for (apiStat in pokemon.stats) {
            apiStat.stat?.name?.let { stat ->
                statMap[stat] = apiStat.baseStat
            }
        }
        val dbStats = statMap.keys.map { stat -> DbStat(name = stat) }
        val statIds = insertStats(dbStats)

        // get stat by name for each stat, map list to pokemon stat list, insert it
        val dbPokemonStats = mutableListOf<DbPokemonStat>()
        for (id in statIds) {
            val dbStat = getStatById(id.toInt())
            dbStat?.let { stat ->
                dbPokemonStats.add(
                    DbPokemonStat(
                        pokemonId = pokemonId,
                        statId = stat.id,
                        value = statMap[stat.name] ?: 0
                    )
                )
            }
        }
        insertPokemonStats(dbPokemonStats)
        return dbPokemon.copy(id = pokemonId).toPokemon()
    }
}