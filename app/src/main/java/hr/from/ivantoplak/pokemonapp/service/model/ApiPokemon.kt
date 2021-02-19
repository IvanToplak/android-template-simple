package hr.from.ivantoplak.pokemonapp.service.model

import com.google.gson.annotations.SerializedName

data class ApiPokemon(
    val id: Int = 0,
    val name: String = "",
    val sprites: ApiSprites? = null,
    val moves: List<ApiMove> = emptyList(),
    val stats: List<ApiStat> = emptyList(),
)

data class ApiMove(val move: ApiMoveDetails? = null)

data class ApiMoveDetails(val name: String = "")

data class ApiSprites(
    @SerializedName("back_default")
    val backDefault: String = "",

    @SerializedName("front_default")
    val frontDefault: String = ""
)

data class ApiStat(
    @SerializedName("base_stat")
    val baseStat: Int = 0,
    val stat: ApiStatDetails? = null
)

data class ApiStatDetails(val name: String = "")






