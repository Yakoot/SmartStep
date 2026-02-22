package dev.mamkin.smartstep.feature.home.domain.repository

import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat
import dev.mamkin.smartstep.core.presentation.model.WeeklyDay
import kotlinx.coroutines.flow.Flow

interface StepTrackerRepository {
    fun observeLast7Days() : Flow<List<WeeklyDay>>
    suspend fun resetTodaySteps()
    suspend fun editSteps(dateEpochMillis: Long, steps: Int)
    fun pauseTracking()
    fun resumeTracking()
    fun isTrackingPaused(): Boolean
}