package dev.mamkin.smartstep.feature.home.data.repository

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import co.touchlab.kermit.Logger
import dev.mamkin.smartstep.core.data.local.db.dao.DailyStatDao
import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat
import dev.mamkin.smartstep.feature.home.domain.repository.StepTrackerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class AndroidStepTrackerRepository(
    private val context: Context,
    private val baselineStorage: StepBaselineStorage,
    private val dailyStatDao: DailyStatDao
) : StepTrackerRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _steps = MutableStateFlow(0)
    override fun observeSteps(): Flow<Int> = _steps

    private var lastTotalSteps: Int? = null
    private var sensorStarted = false

    private val sensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val sensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    init {
        startSensorIfNeeded()
        loadInitialTodaySteps()
    }

    private fun loadInitialTodaySteps() {
        scope.launch {
            val todayEpoch = LocalDate.now().toEpochDay()
            val todayStat = dailyStatDao.getByEpochDay(todayEpoch)
            _steps.value = todayStat?.steps ?: 0
        }
    }

    private fun startSensorIfNeeded() {
        if (sensorStarted) return
        sensorStarted = true

        if (sensor == null) {
            _steps.value = 0
            return
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val totalSteps = event.values[0].toInt()
                lastTotalSteps = totalSteps

                scope.launch {
                    val todayEpoch = LocalDate.now().toEpochDay()
                    val todaySteps = resolveTodaySteps(totalSteps)

                    val updated = DailyStat(
                        epochDay = todayEpoch,
                        steps = todaySteps,
                        kms = todaySteps * 0.0008,
                        kcal = todaySteps * 0.04,
                        minutes = todaySteps / 100.0
                    )

                    dailyStatDao.upsert(updated)
                    _steps.value = todaySteps
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        val registered = sensorManager.registerListener(
            listener,
            sensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )

        if (!registered) {
            Logger.e("Failed to register step counter sensor")
            _steps.value = 0
        }
    }

    override suspend fun resetTodaySteps() {
        val today = LocalDate.now().toEpochDay()
        val currentTotal = lastTotalSteps ?: return

        baselineStorage.saveBaseline(today, currentTotal)

        dailyStatDao.upsert(
            DailyStat(today, 0, 0.0, 0.0, 0.0)
        )

        _steps.value = 0
    }

    override suspend fun editSteps(dateEpochMillis: Long, steps: Int) {
        val todayMillis = LocalDate.now().toEpochDay()

        dailyStatDao.upsert(
            DailyStat(dateEpochMillis, steps, 0.0, 0.0, 0.0)
        )

        if (todayMillis == dateEpochMillis) {
            val currentTotal = lastTotalSteps ?: return
            val newBaseline = currentTotal - steps
            baselineStorage.saveBaseline(todayMillis, newBaseline)

            _steps.value = steps
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
}