package hr.from.ivantoplak.pokemonapp.mappings

import hr.from.ivantoplak.pokemonapp.model.Stat
import hr.from.ivantoplak.pokemonapp.model.StatViewData

fun List<Stat>.toStatViewData(): List<StatViewData> =
    map { StatViewData(name = it.name, value = it.value) }