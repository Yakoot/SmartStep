package dev.mamkin.smartstep.feature.home.data.repository

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dev.mamkin.smartstep.core.data.local.db.dao.DailyStatDao
import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import dev.mamkin.smartstep.core.presentation.model.WeeklyDay
import dev.mamkin.smartstep.feature.home.domain.repository.StepTrackerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class AndroidStepTrackerRepository(
    private val context: Context,
    private val baselineStorage: StepBaselineStorage,
    private val dailyStatDao: DailyStatDao,
    private val userProfileRepository: UserProfileRepository
) : StepTrackerRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var lastTotalSteps: Int? = null
    private var sensorStarted = false
    private var isPaused = false

    private val sensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val sensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    private var sensorListener: SensorEventListener? = null

    init {
        startSensorIfNeeded()
    }

    private fun startSensorIfNeeded() {
        if (sensorStarted || sensor == null) return
        sensorStarted = true

        sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (isPaused) return

                val totalSteps = event.values[0].toInt()
                lastTotalSteps = totalSteps

                scope.launch {
                    val todayEpoch = LocalDate.now().toEpochDay()
                    val todaySteps = resolveTodaySteps(totalSteps)
                    upsertComputedDailyStat(todayEpoch, todaySteps)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sensorManager.registerListener(
            sensorListener,
            sensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun pauseTracking() {
        isPaused = true
        sensorListener?.let {
            sensorManager.unregisterListener(it)
        }
    }

    override fun resumeTracking() {
        isPaused = false
        sensorListener?.let {
            sensorManager.registerListener(
                it,
                sensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun isTrackingPaused(): Boolean = isPaused

    private fun upsertComputedDailyStat(epochDay: Long, steps: Int) {
        scope.launch {
            val height = userProfileRepository.getHeight().first()
            val kcalPerStep = userProfileRepository.getCaloriesPerStep().first()

            val kms = calculateKms(steps, height)
            val kcal = steps * (kcalPerStep ?: 0f).toDouble()
            val minutes = steps / 100.0

            dailyStatDao.upsert(
                DailyStat(epochDay, steps, kms, kcal, minutes)
            )
        }
    }

    private fun calculateKms(steps: Int, heightCm: Int?): Double {
        if (heightCm == null || heightCm <= 0) return 0.0
        val stepLengthCm = heightCm * 0.415
        val distanceMeters = steps * stepLengthCm / 100.0
        return distanceMeters / 1000.0
    }

    override suspend fun resetTodaySteps() {
        val today = LocalDate.now().toEpochDay()
        val currentTotal = lastTotalSteps ?: return

        baselineStorage.saveBaseline(today, currentTotal)
        upsertComputedDailyStat(today, 0)
    }

    override suspend fun editSteps(dateEpochMillis: Long, steps: Int) {
        val todayMillis = LocalDate.now().toEpochDay()

        upsertComputedDailyStat(dateEpochMillis, steps)

        if (todayMillis == dateEpochMillis) {
            val currentTotal = lastTotalSteps ?: return
            val newBaseline = currentTotal - steps
            baselineStorage.saveBaseline(todayMillis, newBaseline)
        }
    }

    private suspend fun resolveTodaySteps(totalSteps: Int): Int {
        val today = LocalDate.now().toEpochDay()
        val baseline = baselineStorage.getBaseline()

        if (baseline == null ||
            baseline.epochDay != today ||
            totalSteps < baseline.totalSteps
        ) {
            baselineStorage.saveBaseline(today, totalSteps)
            return 0
        }
        return totalSteps - baseline.totalSteps
    }

    override fun observeLast7Days(): Flow<List<WeeklyDay>> {
        return combine(
            dailyStatDao.getAllStatsFlow(),
            userProfileRepository.getHeight(),
            userProfileRepository.getCaloriesPerStep()
        ) { allStats, height, kcalPerStep ->
            val todayEpoch = LocalDate.now().toEpochDay()
            val minEpoch = todayEpoch - 6

            val statsMap = allStats
                .filter { it.epochDay >= minEpoch }
                .associateBy { it.epochDay }

            (6 downTo 0).map { daysAgo ->
                val epochDay = todayEpoch - daysAgo
                val steps = statsMap[epochDay]?.steps ?: 0

                val kms = calculateKms(steps, height)
                val kcal = steps * (kcalPerStep ?: 0f).toDouble()
                val minutes = steps / 100.0

                val dailyStat = DailyStat(
                    epochDay = epochDay,
                    steps = steps,
                    kms = kms,
                    kcal = kcal,
                    minutes = minutes
                )

                val localDate = LocalDate.ofEpochDay(epochDay)
                val dayName = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

                WeeklyDay(
                    dailyStat = dailyStat,
                    dayName = dayName,
                    isToday = epochDay == todayEpoch
                )
            }
        }
    }
}