package hr.from.ivantoplak.pokemonapp.pokemon.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonNames(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): ApiPokemonNames

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): ApiPokemon
}
