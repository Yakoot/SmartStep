package dev.mamkin.smartstep.core.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.AndroidSQLiteDriver

fun initDatabase(
    context: Context
): AppDatabase {
    return Room
        .databaseBuilder<AppDatabase>(
            context = context,
            name = "smart_step.db"
        )
        .setDriver(AndroidSQLiteDriver())
        .fallbackToDestructiveMigration(dropAllTables = false)
        .build()
}