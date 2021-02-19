package hr.from.ivantoplak.pokemonapp.service.model

data class ApiPokemonNames(val results: List<ApiPokemonName> = emptyList())

data class ApiPokemonName(val name: String = "")