package dev.mamkin.smartstep.app.di

import dev.mamkin.smartstep.core.data.createDataStoreIos
import dev.mamkin.smartstep.core.data.local.db.AppDatabase
import dev.mamkin.smartstep.core.data.local.db.initDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single { createDataStoreIos() }

    single<AppDatabase> {
        initDatabase()
    }
}