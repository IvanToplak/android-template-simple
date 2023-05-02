package hr.from.ivantoplak.pokemonapp.mappings

import hr.from.ivantoplak.pokemonapp.db.model.DbMove
import hr.from.ivantoplak.pokemonapp.model.Move
import hr.from.ivantoplak.pokemonapp.ui.model.UIMove
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
