package com.project.workmanagertutorial.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.project.workmanagertutorial.MainActivity
import com.project.workmanagertutorial.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppWorkerManager(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    companion object {
        const val CHANNEL_ID = "workManager_channel_id"
        const val NOTIFICATION_ID = 100
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            setForeground(getForegroundInfo())
            Log.d("doWork", "Run Downloader Worker manager")
            Result.success()
        } catch (e: Exception) {
            Log.d("doWork", "exception in doWork ${e.message}")
            Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(100, createNotification())
    }

    private fun createNotification(): Notification {
        val pendingIntent = Intent(applicationContext, MainActivity::class.java).let {
            PendingIntent.getActivity(applicationContext, 0, it, PendingIntent.FLAG_IMMUTABLE)
        }

        val contentView = RemoteViews("com.project.workmanagertutorial", R.layout.notification_layout)
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "Work Manager Message",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.GREEN
            enableVibration(true)
            description = "Work Manager demonstration Tutorial"
        }

        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("WorkManager Message")
            .setContentText("Random User Updated")
            .setContent(contentView)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setColor(Color.RED)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = getSystemService(applicationContext, NotificationManager::class.java)
        notificationManager?.createNotificationChannel(notificationChannel)
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, notification)
        }

       return notification
    }

    private suspend fun getRandomUser() {
        withContext(Dispatchers.IO) {

        }
    }
}