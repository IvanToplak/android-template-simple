package hr.from.ivantoplak.pokemonapp.service.response

data class PokemonNamesResponse(val results: List<PokemonNameResponse> = emptyList())

data class PokemonNameResponse(val name: String = "")