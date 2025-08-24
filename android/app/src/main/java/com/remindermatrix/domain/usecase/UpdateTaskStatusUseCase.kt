package com.remindermatrix.domain.usecase

import com.remindermatrix.data.Task
import com.remindermatrix.data.TaskStatus
import com.remindermatrix.repo.TaskRepository
import javax.inject.Inject

class UpdateTaskStatusUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task, newStatus: TaskStatus) {
        taskRepository.upsertTask(task.copy(status = newStatus))
    }
}
