package dev.mamkin.smartstep.core.data.local.db

import androidx.room.Room
import androidx.sqlite.driver.NativeSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun initDatabase(): AppDatabase {
    val dbFilePath = documentDirectory() + "/smart_step.db"
    return Room
        .databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
        .setDriver(NativeSQLiteDriver())
        .build()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
