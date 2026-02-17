package dev.mamkin.smartstep.feature.home.data.repository

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dev.mamkin.smartstep.feature.home.domain.repository.StepTrackerRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidStepTrackerRepository(
    private val context: Context
) : StepTrackerRepository {

    override fun trackSteps(): Flow<Int> = callbackFlow {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            trySend(0)
            close()
            return@callbackFlow
        }

        var initialSteps: Int? = null

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val totalSteps = event.values[0].toInt()

                if (initialSteps == null) {
                    initialSteps = totalSteps
                }

                val stepsSinceStart = totalSteps - (initialSteps ?: totalSteps)
                trySend(stepsSinceStart)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            listener,
            stepSensor,
            SensorManager.SENSOR_DELAY_UI
        )

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}
