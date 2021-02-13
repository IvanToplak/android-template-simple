package hr.from.ivantoplak.pokemonapp.service

import hr.from.ivantoplak.pokemonapp.service.response.PokemonNamesResponse
import hr.from.ivantoplak.pokemonapp.service.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonNames(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonNamesResponse

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonResponse
}