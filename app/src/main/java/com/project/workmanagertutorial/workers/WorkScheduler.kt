package com.project.workmanagertutorial.workers

import android.content.Context
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

object WorkScheduler {

    suspend fun doPeriodicWork(context: Context) {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        // Set Execution around 07:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 7)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff)
        //Log.d("WorkScheduler", "time difference $minutes")

        // Define Constraints
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .setRequiresDeviceIdle(false)
            .build()

        // Define work interval
        val periodicWorkRequest = PeriodicWorkRequest.Builder(AppWorkerManager::class.java, 24, TimeUnit.HOURS)
                .setInitialDelay(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag("AppWorkerManager")
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "App Worker Manager",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )

    }
}