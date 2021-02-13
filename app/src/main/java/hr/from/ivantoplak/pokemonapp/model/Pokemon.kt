package hr.from.ivantoplak.pokemonapp.model

data class Pokemon(
    val id: Int = 0,
    val name: String = "",
    val frontSpriteUrl: String = "",
    val backSpriteUrl: String = "",
    val moves: List<String> = emptyList(),
    val stats: List<Stat> = emptyList()
) {
}