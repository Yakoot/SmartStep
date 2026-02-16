package dev.mamkin.smartstep.app.di

import androidx.activity.ComponentActivity
import dev.mamkin.smartstep.core.data.createDataStore
import dev.mamkin.smartstep.core.data.service.AndroidPermissionManager
import dev.mamkin.smartstep.core.domain.service.PermissionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single { createDataStore(context = androidContext()) }
    factory<PermissionManager> { AndroidPermissionManager(activity = get()) }
}

fun activityModule(activity: ComponentActivity) = module {
    factory<ComponentActivity> { activity }
}