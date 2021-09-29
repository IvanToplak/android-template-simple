package hr.from.ivantoplak.pokemonapp.ui

/**
 * Screen metadata.
 */
enum class PokemonAppScreen {
    Pokemon,
    Moves,
    Stats,
    Error;

    companion object {
        fun fromRoute(route: String?): PokemonAppScreen =
            when (route?.substringBefore("/")) {
                Pokemon.name -> Pokemon
                Moves.name -> Moves
                Stats.name -> Stats
                Error.name -> Error
                null -> Pokemon
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
