package hr.from.ivantoplak.pokemonapp.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.from.ivantoplak.pokemonapp.common.db.PokemonDatabase.Companion.Version
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbMove
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbPokemon
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbPokemonMove
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbPokemonName
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbPokemonStat
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbStat
import hr.from.ivantoplak.pokemonapp.pokemon.db.PokemonDao

@Database(
    entities = [
        DbPokemon::class,
        DbMove::class,
        DbStat::class,
        DbPokemonMove::class,
        DbPokemonStat::class,
        DbPokemonName::class,
    ],
    version = Version,
    exportSchema = false,
)
abstract class PokemonDatabase : RoomDatabase() {

    companion object {
        const val Name = "pokemon"
        const val Version = 1
    }

    abstract fun pokemonDao(): PokemonDao
}
