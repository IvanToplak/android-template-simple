package hr.from.ivantoplak.pokemonapp.mappings

import hr.from.ivantoplak.pokemonapp.db.model.DbMove
import hr.from.ivantoplak.pokemonapp.model.Move
import hr.from.ivantoplak.pokemonapp.ui.model.MoveViewData

fun DbMove.toMove() = Move(
    id = id,
    name = name,
)

fun Move.toMoveViewData() = MoveViewData(
    id = id,
    name = name,
)

fun List<DbMove>.toMoves() = map { it.toMove() }

fun List<Move>.toMovesViewData() = map { it.toMoveViewData() }
