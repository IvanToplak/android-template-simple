package hr.from.ivantoplak.pokemonapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(
    tableName = "pokemon_stat",
    primaryKeys = ["pokemon_id", "stat_id"],
    foreignKeys = [
        ForeignKey(
            entity = DbPokemon::class,
            parentColumns = ["id"],
            childColumns = ["pokemon_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = DbStat::class,
            parentColumns = ["id"],
            childColumns = ["stat_id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("stat_id")]
)
data class DbPokemonStat(
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int = 0,
    @ColumnInfo(name = "stat_id") val statId: Int = 0,
    @ColumnInfo(defaultValue = "0") val value: Int = 0
)
