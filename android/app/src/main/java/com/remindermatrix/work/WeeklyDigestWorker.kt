package com.remindermatrix.work

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.remindermatrix.R
import com.remindermatrix.repo.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class WeeklyDigestWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    private val taskRepository: TaskRepository
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val tasks = taskRepository.allTasks().first()
        val pendingTasks = tasks.filter { it.status == com.remindermatrix.data.TaskStatus.PENDING }

        if (pendingTasks.isNotEmpty()) {
            val title = "Weekly Digest"
            val content = "You have ${pendingTasks.size} pending tasks for the week."
            showSummaryNotification(title, content)
        }

        return Result.success()
    }

    private fun showSummaryNotification(title: String, content: String) {
        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(appContext, "reminder_channel")
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_notification)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .build()

        notificationManager.notify(title.hashCode(), notification)
    }
}
