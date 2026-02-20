package dev.mamkin.smartstep.core.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyStatDao {
    @Query("SELECT * FROM DailyStat ")
    suspend fun getAllStats(): List<DailyStat>

    @Query("SELECT * FROM DailyStat ")
    fun getAllStatsFlow(): Flow<List<DailyStat>>

    @Query("SELECT * FROM DailyStat WHERE epochDay = :epochDay")
    fun getStatsByDate(epochDay: Long): DailyStat

    @Query("SELECT * FROM DailyStat WHERE epochDay = :epochDay")
    fun getStatsByDateFlow(
        epochDay: Long
    ): Flow<DailyStat>

    @Query("SELECT * FROM DailyStat WHERE epochDay = :epochDay")
    suspend fun getByDay(epochDay: Long): DailyStat?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(stat: DailyStat)
}