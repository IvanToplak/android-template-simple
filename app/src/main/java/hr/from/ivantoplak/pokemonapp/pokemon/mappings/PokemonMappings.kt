package hr.from.ivantoplak.pokemonapp.pokemon.mappings

import hr.from.ivantoplak.pokemonapp.pokemon.api.ApiPokemon
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbMove
import hr.from.ivantoplak.pokemonapp.pokemon.db.DbPokemon
import hr.from.ivantoplak.pokemonapp.pokemon.model.Pokemon
import hr.from.ivantoplak.pokemonapp.pokemon.ui.pokemon.UIPokemon

fun ApiPokemon.toDbMoves(): List<DbMove> =
    this.moves.mapNotNull { it.move?.name }.map { name -> DbMove(name = name) }

fun ApiPokemon.toDbPokemon(): DbPokemon = DbPokemon(
    name = name,
    frontSpriteUrl = sprites?.frontDefault ?: "",
    backSpriteUrl = sprites?.backDefault ?: "",
)

fun Pokemon.toUIPokemon(): UIPokemon = UIPokemon(
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
