package dev.mamkin.smartstep.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import dev.mamkin.smartstep.core.data.local.db.dao.DailyStatDao
import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat

@Database(
    entities = [DailyStat::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dailyStatDao: DailyStatDao
}

@Suppress("KotlinNoActualForExpect", "NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}