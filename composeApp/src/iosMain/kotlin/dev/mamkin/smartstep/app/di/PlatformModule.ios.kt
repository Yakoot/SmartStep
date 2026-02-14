package dev.mamkin.smartstep.app.di

import dev.mamkin.smartstep.core.data.createDataStoreIos
import org.koin.dsl.module

actual val platformModule = module {
    single { createDataStoreIos() }
}