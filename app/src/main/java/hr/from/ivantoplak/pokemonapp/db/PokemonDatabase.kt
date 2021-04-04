package hr.from.ivantoplak.pokemonapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.from.ivantoplak.pokemonapp.db.PokemonDatabase.Companion.VERSION
import hr.from.ivantoplak.pokemonapp.db.dao.PokemonDao
import hr.from.ivantoplak.pokemonapp.db.model.DbMove
import hr.from.ivantoplak.pokemonapp.db.model.DbPokemon
import hr.from.ivantoplak.pokemonapp.db.model.DbPokemonMove
import hr.from.ivantoplak.pokemonapp.db.model.DbPokemonName
import hr.from.ivantoplak.pokemonapp.db.model.DbPokemonStat
import hr.from.ivantoplak.pokemonapp.db.model.DbStat

@Database(
    entities = [
        DbPokemon::class,
        DbMove::class,
        DbStat::class,
        DbPokemonMove::class,
        DbPokemonStat::class,
        DbPokemonName::class
    ],
    version = VERSION,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    companion object {
        const val NAME = "pokemon"
        const val VERSION = 1
    }

    abstract fun pokemonDao(): PokemonDao
}
