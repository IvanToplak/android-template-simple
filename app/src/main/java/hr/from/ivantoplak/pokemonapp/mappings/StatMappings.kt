package hr.from.ivantoplak.pokemonapp.mappings

import hr.from.ivantoplak.pokemonapp.db.model.DbStatNameValue
import hr.from.ivantoplak.pokemonapp.model.Stat
import hr.from.ivantoplak.pokemonapp.ui.model.StatViewData

fun DbStatNameValue.toStat(): Stat = Stat(
    id = id,
    value = value,
    name = name,
)

fun Stat.toStatViewData(): StatViewData = StatViewData(
    id = id,
    value = value,
    name = name,
)

fun List<DbStatNameValue>.toStats(): List<Stat> = map { it.toStat() }

fun List<Stat>.toStatsViewData(): List<StatViewData> = map { it.toStatViewData() }
