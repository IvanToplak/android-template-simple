package hr.from.ivantoplak.pokemonapp.ui

/**
 * Screen metadata.
 */
enum class PokemonAppScreen(
    val title: String,
) {
    Pokemon(
        title = "Pokemon",
    ),
    Moves(
        title = "Moves",
    ),
    Stats(
        title = "Stats",
    );

    companion object {
        fun fromRoute(route: String?): PokemonAppScreen =
            when (route?.substringBefore("/")) {
                Pokemon.name -> Pokemon
                Moves.name -> Moves
                Stats.name -> Stats
                null -> Pokemon
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
