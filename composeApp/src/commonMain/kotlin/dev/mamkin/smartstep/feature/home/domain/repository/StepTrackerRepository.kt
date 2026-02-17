package dev.mamkin.smartstep.feature.home.domain.repository

import kotlinx.coroutines.flow.Flow

interface StepTrackerRepository {
    fun trackSteps() : Flow<Int>
}