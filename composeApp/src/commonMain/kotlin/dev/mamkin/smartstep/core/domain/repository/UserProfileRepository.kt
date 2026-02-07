package dev.mamkin.smartstep.core.domain.repository

import dev.mamkin.smartstep.core.domain.model.Gender
import dev.mamkin.smartstep.core.domain.model.HeightUnit
import dev.mamkin.smartstep.core.domain.model.UnitSystem
import dev.mamkin.smartstep.core.domain.model.WeightUnit
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun saveHeight(heightCm: Int)
    fun getHeight(): Flow<Int?>

    suspend fun saveWeight(weightKg: Float)
    fun getWeight(): Flow<Float?>

    suspend fun saveGender(gender: Gender)
    fun getGender(): Flow<Gender?>

    suspend fun saveUnit(unit: UnitSystem)
    fun getUnit(): Flow<UnitSystem>

    fun getHeightUnit(): Flow<HeightUnit>
    fun getWeightUnit(): Flow<WeightUnit>
}