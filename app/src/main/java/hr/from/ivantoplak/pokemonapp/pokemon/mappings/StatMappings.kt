package hr.from.ivantoplak.pokemonapp.pokemon.mappings

import hr.from.ivantoplak.pokemonapp.pokemon.db.DbStatNameValue
import hr.from.ivantoplak.pokemonapp.pokemon.model.Stat
import hr.from.ivantoplak.pokemonapp.pokemon.ui.stats.UIStat
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
