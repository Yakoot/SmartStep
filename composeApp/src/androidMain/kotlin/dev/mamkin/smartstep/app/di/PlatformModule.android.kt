package dev.mamkin.smartstep.app.di

import dev.mamkin.smartstep.core.data.createDataStore
import org.koin.dsl.module

actual val platformModule = module {
    single { createDataStore(context = get()) }
}