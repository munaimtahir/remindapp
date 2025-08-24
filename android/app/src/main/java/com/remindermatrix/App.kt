package com.remindermatrix

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.remindermatrix.work.DailySnapshotWorker
import com.remindermatrix.work.WeeklyDigestWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        scheduleDigestWorkers()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ReminderChannel"
            val descriptionText = "Channel for task reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("reminder_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleDigestWorkers() {
        val workManager = WorkManager.getInstance(this)

        // Schedule Weekly Digest (Sunday 5 PM)
        val weeklyRequest = PeriodicWorkRequestBuilder<WeeklyDigestWorker>(7, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(Calendar.SUNDAY, 17, 0, isDaily = false), TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            "WeeklyDigest",
            ExistingPeriodicWorkPolicy.KEEP,
            weeklyRequest
        )

        // Schedule Daily Snapshot (Weekdays 7 AM)
        val dailyRequest = PeriodicWorkRequestBuilder<DailySnapshotWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(Calendar.MONDAY, 7, 0, isDaily = true), TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            "DailySnapshot",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyRequest
        )
    }

    private fun calculateInitialDelay(dayOfWeek: Int, hour: Int, minute: Int, isDaily: Boolean): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (isDaily) {
                // If it's for a daily worker, we just need to check the time.
                // If the time has passed for today, schedule for tomorrow.
                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
                // Now, ensure it's a weekday
                while (get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            } else {
                // For weekly, set the specific day of the week.
                set(Calendar.DAY_OF_WEEK, dayOfWeek)
                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }
        }
        return calendar.timeInMillis - System.currentTimeMillis()
    }
}
