package hr.from.ivantoplak.pokemonapp.service.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val id: Int = 0,
    val name: String = "",
    val sprites: Sprites? = null,
    val moves: List<Move> = emptyList(),
    val stats: List<Stat> = emptyList(),
)

data class Move(val move: MoveDetails? = null)

data class MoveDetails(val name: String = "")

data class Sprites(
    @SerializedName("back_default")
    val backDefault: String = "",

    @SerializedName("front_default")
    val frontDefault: String = ""
)

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int = 0,
    val stat: StatDetails? = null
)

data class StatDetails(val name: String = "")






