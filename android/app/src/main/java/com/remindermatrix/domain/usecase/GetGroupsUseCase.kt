package com.remindermatrix.domain.usecase

import com.remindermatrix.data.Group
import com.remindermatrix.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Group>> {
        return taskRepository.allGroups()
    }
}
