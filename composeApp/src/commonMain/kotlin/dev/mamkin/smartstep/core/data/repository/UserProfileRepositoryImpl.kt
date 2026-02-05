package dev.mamkin.smartstep.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.mamkin.smartstep.core.domain.model.Gender
import dev.mamkin.smartstep.core.domain.model.UnitPreference
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl (
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

    override suspend fun saveUnitPreference(unit: UnitPreference) {
        dataStore.edit { preferences ->
            preferences[UNIT_PREFERENCE_KEY] = unit.name
        }
    }

    override fun getUnitPreference(): Flow<UnitPreference> {
        return dataStore.data.map { preferences ->
            UnitPreference.fromName(preferences[UNIT_PREFERENCE_KEY])
        }
    }

    private companion object Keys {

        private val HEIGHT_KEY = intPreferencesKey("PROFILE_HEIGHT")
        private val WEIGHT_KEY = floatPreferencesKey("PROFILE_WEIGHT")
        private val GENDER_KEY = stringPreferencesKey("PROFILE_GENDER")
        private val UNIT_PREFERENCE_KEY = stringPreferencesKey("PROFILE_UNIT_PREFERENCE")
    }
}