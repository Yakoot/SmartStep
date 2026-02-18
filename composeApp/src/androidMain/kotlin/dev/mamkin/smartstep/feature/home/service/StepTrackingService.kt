package dev.mamkin.smartstep.feature.home.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import dev.mamkin.smartstep.composeapp.R
import dev.mamkin.smartstep.feature.home.domain.repository.StepTrackerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class StepTrackingService : Service() {

    companion object {
        private const val CHANNEL_ID = "step_tracking_channel"
        private const val NOTIFICATION_ID = 1001

        fun startIntent(context: Context) = Intent(context, StepTrackingService::class.java)
    }

    private val stepTrackerRepository: StepTrackerRepository by inject()
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var trackingJob: Job? = null
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, buildNotification(0))

        if (trackingJob?.isActive != true) {
            trackingJob = serviceScope.launch {
                stepTrackerRepository.trackSteps().collectLatest { steps ->
                    notificationManager.notify(NOTIFICATION_ID, buildNotification(steps))
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Step Tracking",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Shows your step count progress"
            setShowBadge(false)
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildNotification(steps: Int): Notification {
        // Get the layouts to use in the custom notification.
        val notificationLayout = RemoteViews(packageName, R.layout.notification_small)
        notificationLayout.setTextViewText(R.id.stepsCount, steps.toString())
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_large)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_steps)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }
}
