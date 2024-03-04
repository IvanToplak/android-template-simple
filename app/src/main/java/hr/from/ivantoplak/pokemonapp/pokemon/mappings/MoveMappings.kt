package hr.from.ivantoplak.pokemonapp.pokemon.mappings

import hr.from.ivantoplak.pokemonapp.pokemon.db.DbMove
import hr.from.ivantoplak.pokemonapp.pokemon.model.Move
import hr.from.ivantoplak.pokemonapp.pokemon.ui.moves.UIMove
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun DbMove.toMove() = Move(
    id = id,
    name = name,
)

fun Move.toUIMove() = UIMove(
    id = id,
    name = name,
)

fun List<DbMove>.toMoves() = map { it.toMove() }

fun List<Move>.toUIMoves(): ImmutableList<UIMove> = map { it.toUIMove() }.toImmutableList()
