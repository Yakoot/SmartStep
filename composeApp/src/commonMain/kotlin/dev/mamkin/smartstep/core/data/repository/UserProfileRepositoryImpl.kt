package dev.mamkin.smartstep.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.mamkin.smartstep.core.domain.mappers.toHeightUnit
import dev.mamkin.smartstep.core.domain.mappers.toWeightUnit
import dev.mamkin.smartstep.core.domain.model.Gender
import dev.mamkin.smartstep.core.domain.model.HeightUnit
import dev.mamkin.smartstep.core.domain.model.UnitSystem
import dev.mamkin.smartstep.core.domain.model.WeightUnit
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : UserProfileRepository {
    override suspend fun saveHeight(heightCm: Int) {
        dataStore.edit { preferences ->
            preferences[HEIGHT_KEY] = heightCm
        }
    }

    override fun getHeight(): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[HEIGHT_KEY]
        }
    }

    override suspend fun saveWeight(weightKg: Float) {
        dataStore.edit { preferences ->
            preferences[WEIGHT_KEY] = weightKg
        }
    }

    override fun getWeight(): Flow<Float?> {
        return dataStore.data.map { preferences ->
            preferences[WEIGHT_KEY]
        }
    }

    override suspend fun saveGender(gender: Gender) {
        dataStore.edit { preferences ->
            preferences[GENDER_KEY] = gender.name
        }
    }

    override fun getGender(): Flow<Gender?> {
        return dataStore.data.map { preferences ->
            Gender.fromName(preferences[GENDER_KEY])
        }
    }

    override suspend fun saveUnit(unit: UnitSystem) {
        dataStore.edit { preferences ->
            preferences[UNIT_SYSTEM_KEY] = unit.name
        }
    }

    override fun getUnit(): Flow<UnitSystem> {
        return dataStore.data.map { preferences ->
            UnitSystem.fromName(preferences[UNIT_SYSTEM_KEY])
        }
    }

    override fun getHeightUnit(): Flow<HeightUnit> {
        return dataStore.data.map { preferences ->
            UnitSystem.fromName(preferences[UNIT_SYSTEM_KEY]).toHeightUnit()
        }
    }

    override fun getWeightUnit(): Flow<WeightUnit> {
        return dataStore.data.map { preferences ->
            UnitSystem.fromName(preferences[UNIT_SYSTEM_KEY]).toWeightUnit()
        }
    }

    override suspend fun setStepGoal(stepGoal: Int) {
        dataStore.edit { preferences ->
            preferences[STEP_GOAL_KEY] = stepGoal
        }
    }

    override fun getStepGoal(): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[STEP_GOAL_KEY]
        }
    }

    override suspend fun setProfileSetupCompleted(boolean: Boolean) {
        dataStore.edit { preferences ->
            preferences[SETUP_COMPLETED_KEY] = if (boolean) 1 else 0
        }
    }

    override suspend fun isProfileSetupCompleted(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[SETUP_COMPLETED_KEY] == 1
        }.first()
    }

    override fun getCaloriesPerStep(): Flow<Float> {
        return combine(getWeight(), getGender()) { weight, gender ->
            val weightKg = weight ?: DEFAULT_WEIGHT_KG
            val genderFactor = when (gender) {
                Gender.Male -> 1.0f
                Gender.Female -> 0.9f
                null -> 1.0f
            }
            weightKg * 0.0005f * genderFactor
        }
    }

    private companion object Keys {
        private const val DEFAULT_WEIGHT_KG = 65f

        private val HEIGHT_KEY = intPreferencesKey("PROFILE_HEIGHT")
        private val WEIGHT_KEY = floatPreferencesKey("PROFILE_WEIGHT")
        private val GENDER_KEY = stringPreferencesKey("PROFILE_GENDER")
        private val UNIT_SYSTEM_KEY = stringPreferencesKey("PROFILE_UNIT_SYSTEM")

        private val STEP_GOAL_KEY = intPreferencesKey("PROFILE_STEP_GOAL")
        private val SETUP_COMPLETED_KEY = intPreferencesKey("PROFILE_SETUP_COMPLETED")
    }
}