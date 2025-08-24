package com.remindermatrix.domain.usecase

import com.remindermatrix.data.Task
import com.remindermatrix.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return taskRepository.allTasks()
    }
}
