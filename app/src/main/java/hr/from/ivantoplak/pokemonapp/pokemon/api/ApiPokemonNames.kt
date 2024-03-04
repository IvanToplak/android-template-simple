package hr.from.ivantoplak.pokemonapp.pokemon.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPokemonNames(val results: List<ApiPokemonName> = emptyList())

@JsonClass(generateAdapter = true)
data class ApiPokemonName(val name: String = "")
