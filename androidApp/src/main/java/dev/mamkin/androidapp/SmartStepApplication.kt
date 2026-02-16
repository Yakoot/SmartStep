package dev.mamkin.androidapp

import android.app.Application
import dev.mamkin.smartstep.app.di.appModule
import dev.mamkin.smartstep.app.di.initKoin
import dev.mamkin.smartstep.app.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SmartStepApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SmartStepApplication)
            androidLogger()
        }
    }
}