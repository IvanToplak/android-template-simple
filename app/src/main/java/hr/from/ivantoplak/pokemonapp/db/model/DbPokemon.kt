package hr.from.ivantoplak.pokemonapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon",
    indices = [Index("name", unique = true)]
)
data class DbPokemon(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(defaultValue = "") val name: String = "",
    @ColumnInfo(name = "front_sprite_url", defaultValue = "") val frontSpriteUrl: String = "",
    @ColumnInfo(name = "back_sprite_url", defaultValue = "") val backSpriteUrl: String = ""
)
