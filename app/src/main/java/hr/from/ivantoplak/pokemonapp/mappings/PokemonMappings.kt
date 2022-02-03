package hr.from.ivantoplak.pokemonapp.mappings

import hr.from.ivantoplak.pokemonapp.db.model.DbMove
import hr.from.ivantoplak.pokemonapp.db.model.DbPokemon
import hr.from.ivantoplak.pokemonapp.model.Pokemon
import hr.from.ivantoplak.pokemonapp.service.model.ApiPokemon
import hr.from.ivantoplak.pokemonapp.ui.model.PokemonViewData

fun ApiPokemon.toDbMoves(): List<DbMove> =
    this.moves.mapNotNull { it.move?.name }.map { name -> DbMove(name = name) }

fun ApiPokemon.toDbPokemon(): DbPokemon = DbPokemon(
    name = name,
    frontSpriteUrl = sprites?.frontDefault ?: "",
    backSpriteUrl = sprites?.backDefault ?: "",
)

fun Pokemon.toPokemonViewData(): PokemonViewData = PokemonViewData(
    id = id,
    name = name,
    frontSpriteUrl = frontSpriteUrl,
    backSpriteUrl = backSpriteUrl,
)

fun DbPokemon.toPokemon(): Pokemon = Pokemon(
    id = id,
    name = name,
    frontSpriteUrl = frontSpriteUrl,
    backSpriteUrl = backSpriteUrl,
)
