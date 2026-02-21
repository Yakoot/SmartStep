package dev.mamkin.smartstep.feature.home.presentation.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class EditStepDate(
    val year: Int,
    val month: Int,
    val day: Int
) {
    fun toEpochMillis() : Long {
        return LocalDate(
            year = year,
            month = month,
            day = day
        ).toEpochDays()
    }

    fun format() : String {
        return "$year/$month/$day"
    }

    companion object {
        fun today(): EditStepDate {
            val today = Clock.System.now()
            val nowLocal = today.toLocalDateTime(TimeZone.currentSystemDefault())

            return EditStepDate(
                year = nowLocal.year,
                month = nowLocal.month.number,
                day = nowLocal.day
            )
        }
    }
}
