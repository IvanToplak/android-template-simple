package hr.from.ivantoplak.pokemonapp.mappings

import hr.from.ivantoplak.pokemonapp.db.model.DbStatNameValue
import hr.from.ivantoplak.pokemonapp.model.Stat
import hr.from.ivantoplak.pokemonapp.ui.model.UIStat
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun DbStatNameValue.toStat(): Stat = Stat(
    id = id,
    value = value,
    name = name,
)

fun Stat.toUIStat(): UIStat = UIStat(
    id = id,
    value = value,
    name = name,
)

fun List<DbStatNameValue>.toStats(): List<Stat> = map { it.toStat() }

fun List<Stat>.toUIStats(): ImmutableList<UIStat> = map { it.toUIStat() }.toImmutableList()
