package dev.mamkin.smartstep.core.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyStat(
    @PrimaryKey val epochDay: Long,
    val steps: Int,
    val kms: Double,
    val kcal: Double,
    val minutes: Double,
)