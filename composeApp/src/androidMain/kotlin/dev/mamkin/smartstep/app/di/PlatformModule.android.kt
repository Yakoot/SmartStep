package dev.mamkin.smartstep.app.di

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import dev.mamkin.smartstep.core.data.createDataStore
import dev.mamkin.smartstep.core.data.local.db.AppDatabase
import dev.mamkin.smartstep.core.data.local.db.initDatabase
import dev.mamkin.smartstep.core.presentation.utils.AndroidPlatformActionManager
import dev.mamkin.smartstep.core.presentation.utils.PermissionManager
import dev.mamkin.smartstep.core.presentation.utils.PlatformActionManager
import dev.mamkin.smartstep.feature.home.data.repository.AndroidStepTrackerRepository
import dev.mamkin.smartstep.feature.home.data.repository.StepBaselineStorage
import dev.mamkin.smartstep.feature.home.domain.repository.StepTrackerRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<DataStore<Preferences>> {
        createDataStore(
            context = androidContext()
        )
    }

    single<AppDatabase> {
        initDatabase(
            context = androidContext()
        )
    }

    single<PlatformActionManager> { AndroidPlatformActionManager(androidContext()) }

    single<PermissionManager> {
        PermissionManager(
            context = androidContext(),
            dataStore = get()
        )
    }

    single<StepBaselineStorage> {
        StepBaselineStorage(
            dataStore = get() 
        )
    }

    single<StepTrackerRepository> {
        AndroidStepTrackerRepository(
            context = androidContext(),
            baselineStorage = get(),
            dailyStatDao = get()
        )
    }
}