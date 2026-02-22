package dev.mamkin.smartstep.core.presentation.model

import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat

data class WeeklyDay(
    val dailyStat: DailyStat,
    val dayName: String,
    val isToday: Boolean
)