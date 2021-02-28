package hr.from.ivantoplak.pokemonapp.service.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPokemon(
    val id: Int = 0,
    val name: String = "",
    val sprites: ApiSprites? = null,
    val moves: List<ApiMove> = emptyList(),
    val stats: List<ApiStat> = emptyList(),
)

@JsonClass(generateAdapter = true)
data class ApiMove(val move: ApiMoveDetails? = null)

@JsonClass(generateAdapter = true)
data class ApiMoveDetails(val name: String = "")

@JsonClass(generateAdapter = true)
data class ApiSprites(
    @Json(name = "back_default")
    val backDefault: String = "",

    @Json(name = "front_default")
    val frontDefault: String = ""
)

@JsonClass(generateAdapter = true)
data class ApiStat(
    @Json(name = "base_stat")
    val baseStat: Int = 0,
    val stat: ApiStatDetails? = null
)

@JsonClass(generateAdapter = true)
data class ApiStatDetails(val name: String = "")
