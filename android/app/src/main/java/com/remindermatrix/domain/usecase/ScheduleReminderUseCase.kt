package com.remindermatrix.domain.usecase

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.remindermatrix.data.Task
import com.remindermatrix.data.TaskCategory
import com.remindermatrix.work.ReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

class ScheduleReminderUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    operator fun invoke(task: Task) {
        // Cancel any existing reminders for this task to avoid duplicates
        workManager.cancelAllWorkByTag(task.id)

        val now = LocalDateTime.now()
        val requests = mutableListOf<OneTimeWorkRequest>()

        when (task.category) {
            TaskCategory.CAT1 -> { // (≤4h): remind at creation+1h, then hourly until due
                val firstReminder = now.plusHours(1)
                if (firstReminder.isBefore(task.dueDate)) {
                    requests.add(createWorkRequest(task, Duration.between(now, firstReminder)))
                }
                var nextReminder = firstReminder.plusHours(1)
                while (nextReminder.isBefore(task.dueDate)) {
                    requests.add(createWorkRequest(task, Duration.between(now, nextReminder)))
                    nextReminder = nextReminder.plusHours(1)
                }
            }
            TaskCategory.CAT2 -> { // (≥1d): remind at T-24h, then at due
                scheduleAt(now, task, requests, task.dueDate.minusHours(24))
                scheduleAt(now, task, requests, task.dueDate)
            }
            TaskCategory.CAT3 -> { // (≥3d): remind at T-72h, T-24h, then at due
                scheduleAt(now, task, requests, task.dueDate.minusHours(72))
                scheduleAt(now, task, requests, task.dueDate.minusHours(24))
                scheduleAt(now, task, requests, task.dueDate)
            }
            TaskCategory.CAT4 -> { // (>4d): remind weekly until inside 3d window, then follow CAT3
                var weeklyReminder = task.dueDate.minusDays(7)
                while(Duration.between(now, weeklyReminder).toDays() > 3) {
                    scheduleAt(now, task, requests, weeklyReminder)
                    weeklyReminder = weeklyReminder.minusDays(7)
                }
                // Fall into CAT3 logic
                scheduleAt(now, task, requests, task.dueDate.minusHours(72))
                scheduleAt(now, task, requests, task.dueDate.minusHours(24))
                scheduleAt(now, task, requests, task.dueDate)
            }
        }
        if (requests.isNotEmpty()) {
            workManager.enqueue(requests)
        }
    }

    private fun scheduleAt(
        now: LocalDateTime,
        task: Task,
        requests: MutableList<OneTimeWorkRequest>,
        time: LocalDateTime
    ) {
        if (time.isAfter(now)) {
            requests.add(createWorkRequest(task, Duration.between(now, time)))
        }
    }

    private fun createWorkRequest(task: Task, delay: Duration): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay)
            .setInputData(workDataOf("TASK_ID" to task.id))
            .addTag(task.id) // Tag work with task ID for cancellation
            .build()
    }
}
