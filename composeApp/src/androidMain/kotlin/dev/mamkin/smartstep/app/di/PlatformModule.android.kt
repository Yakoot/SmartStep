package dev.mamkin.smartstep.app.di

import dev.mamkin.smartstep.core.data.createDataStore
import dev.mamkin.smartstep.core.presentation.utils.AndroidPlatformActionManager
import dev.mamkin.smartstep.core.presentation.utils.PermissionManager
import dev.mamkin.smartstep.core.presentation.utils.PlatformActionManager
import org.koin.dsl.module

actual val platformModule = module {
    single { createDataStore(context = get()) }
    single<PlatformActionManager>{ AndroidPlatformActionManager(get()) }
    single { PermissionManager(context = get(),dataStore = get()) }
}