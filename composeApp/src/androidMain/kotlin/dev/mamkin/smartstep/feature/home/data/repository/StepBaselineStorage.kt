package dev.mamkin.smartstep.feature.home.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first

class StepBaselineStorage(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val BASELINE_STEPS = intPreferencesKey("baseline_steps")
        val BASELINE_DAY = longPreferencesKey("baseline_day")
    }

    suspend fun saveBaseline(epochDay: Long, totalSteps: Int) {
        dataStore.edit {
            it[Keys.BASELINE_DAY] = epochDay
            it[Keys.BASELINE_STEPS] = totalSteps
        }
    }

    suspend fun getBaseline(): Baseline? {
        val prefs = dataStore.data.first()

        val day = prefs[Keys.BASELINE_DAY] ?: return null
        val steps = prefs[Keys.BASELINE_STEPS] ?: return null

        return Baseline(day, steps)
    }

    data class Baseline(
        val epochDay: Long,
        val totalSteps: Int
    )
}