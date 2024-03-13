package hr.from.ivantoplak.pokemonapp.common.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.DateTimeException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class OffsetDateTimeAdapter {

    @ToJson
    fun toJson(dateTime: OffsetDateTime): String? = try {
        Formatter.format(dateTime)
    } catch (e: DateTimeException) {
        null
    }

    @FromJson
    fun fromJson(json: String): OffsetDateTime? = try {
        OffsetDateTime.parse(json, Formatter)
    } catch (e: DateTimeParseException) {
        null
    }

    companion object {
        private val Formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    }
}
