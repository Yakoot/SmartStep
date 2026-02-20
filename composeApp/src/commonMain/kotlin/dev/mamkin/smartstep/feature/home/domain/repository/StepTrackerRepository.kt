package dev.mamkin.smartstep.feature.home.domain.repository

import kotlinx.coroutines.flow.Flow

interface StepTrackerRepository {
    suspend fun resetTodaySteps()
    suspend fun editSteps(dateEpochMillis: Long, steps: Int)
    fun observeSteps() : Flow<Int>
}