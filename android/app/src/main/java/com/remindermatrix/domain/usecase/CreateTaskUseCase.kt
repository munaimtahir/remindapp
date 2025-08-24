package com.remindermatrix.domain.usecase

import com.remindermatrix.data.Task
import com.remindermatrix.repo.TaskRepository
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val scheduleReminderUseCase: ScheduleReminderUseCase
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.upsertTask(task)
        scheduleReminderUseCase(task)
    }
}
