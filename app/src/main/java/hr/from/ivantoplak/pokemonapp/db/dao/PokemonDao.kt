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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPokemon(pokemon: DbPokemon): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMoves(moves: List<DbMove>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertStats(stats: List<DbStat>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPokemonMoves(stats: List<DbPokemonMove>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPokemonStats(stats: List<DbPokemonStat>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPokemonNames(stats: List<DbPokemonName>)

    @Query("SELECT id FROM pokemon")
    abstract suspend fun getPokemonIds(): List<Long>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    abstract suspend fun getPokemonById(id: Int): DbPokemon?

    @Query("SELECT * FROM pokemon WHERE name = :name")
    abstract suspend fun getPokemonByName(name: String): DbPokemon?

    @Query("SELECT * FROM pokemon_name")
    abstract suspend fun getPokemonNames(): List<DbPokemonName>

    @Query("SELECT m.* FROM pokemon_move pm JOIN move m ON m.id = pm.move_id WHERE pm.pokemon_id = :pokemonId ORDER BY m.name")
    abstract fun getMoves(pokemonId: Int): Flow<List<DbMove>>

    @Query("SELECT * FROM move WHERE name = :name")
    abstract suspend fun getMoveByName(name: String): DbMove?

    @Query("SELECT s.id, s.name, ps.value FROM pokemon_stat ps JOIN stat s ON s.id = ps.stat_id WHERE ps.pokemon_id = :pokemonId ORDER BY s.name")
    abstract fun getStats(pokemonId: Int): Flow<List<DbStatNameValue>>

    @Query("SELECT * FROM stat WHERE name = :name")
    abstract suspend fun getStatByName(name: String): DbStat?

    @Query("SELECT * FROM stat WHERE id = :id")
    abstract suspend fun getStatById(id: Int): DbStat?

    @Transaction
    open suspend fun savePokemonData(pokemon: ApiPokemon): Pokemon {
        val dbPokemon = pokemon.toDbPokemon()

        // save pokemon to db
        var pokemonId = insertPokemon(dbPokemon).toInt()
        if (pokemonId == -1) {
            pokemonId = getPokemonByName(dbPokemon.name)?.id ?: 0
        }

        // save moves to db
        val dbMoves = pokemon.toDbMoves()
        insertMoves(dbMoves)
        val dbPokemonMoves = mutableListOf<DbPokemonMove>()
        for (move in dbMoves) {
            getMoveByName(move.name)?.let { dbMove ->
                dbPokemonMoves.add(DbPokemonMove(moveId = dbMove.id, pokemonId = pokemonId))
            }
        }
        insertPokemonMoves(dbPokemonMoves)

        // save stats to db
        val statMap = mutableMapOf<String, Int>()
        for (apiStat in pokemon.stats) {
            apiStat.stat?.name?.let { stat ->
                statMap[stat] = apiStat.baseStat
            }
        }
        val dbStats = statMap.keys.map { stat -> DbStat(name = stat) }
        insertStats(dbStats)

        // get stat by name for each stat, map list to pokemon stat list, insert it
        val dbPokemonStats = mutableListOf<DbPokemonStat>()
        for (stat in statMap.keys) {
            getStatByName(stat)?.let { dbStat ->
                dbPokemonStats.add(
                    DbPokemonStat(
                        pokemonId = pokemonId,
                        statId = dbStat.id,
                        value = statMap[dbStat.name] ?: 0
                    )
                )
            }
        }
        insertPokemonStats(dbPokemonStats)
        return dbPokemon.copy(id = pokemonId).toPokemon()
    }
}
