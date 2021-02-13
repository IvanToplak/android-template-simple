package hr.from.ivantoplak.pokemonapp.mappings

import hr.from.ivantoplak.pokemonapp.model.Pokemon
import hr.from.ivantoplak.pokemonapp.model.PokemonViewData
import hr.from.ivantoplak.pokemonapp.model.Stat
import hr.from.ivantoplak.pokemonapp.service.response.PokemonResponse

fun PokemonResponse.toPokemon(): Pokemon = Pokemon(
    id = id,
    name = name,
    frontSpriteUrl = sprites?.frontDefault ?: "",
    backSpriteUrl = sprites?.backDefault ?: "",
    moves = moves.map { it.move?.name ?: "" },
    stats = stats.map { Stat(name = it.stat?.name ?: "", value = it.baseStat) }
)

fun Pokemon.toPokemonViewData(): PokemonViewData = PokemonViewData(
    id = id,
    name = name,
    frontSpriteUrl = frontSpriteUrl,
    backSpriteUrl = backSpriteUrl
)


