package hr.from.ivantoplak.pokemonapp.pokemon.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "pokemon_move",
    primaryKeys = ["pokemon_id", "move_id"],
    foreignKeys = [
        ForeignKey(
            entity = DbPokemon::class,
            parentColumns = ["id"],
            childColumns = ["pokemon_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = DbMove::class,
            parentColumns = ["id"],
            childColumns = ["move_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("move_id")],
)
data class DbPokemonMove(
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int = 0,
    @ColumnInfo(name = "move_id") val moveId: Int = 0,
)
