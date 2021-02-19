package hr.from.ivantoplak.pokemonapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "stat",
    indices = [Index("name", unique = true)]
)
data class DbStat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(defaultValue = "") val name: String = "",
)
