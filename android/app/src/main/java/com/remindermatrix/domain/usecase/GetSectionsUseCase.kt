package com.remindermatrix.domain.usecase

import com.remindermatrix.data.Section
import com.remindermatrix.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Section>> {
        return taskRepository.allSections()
    }
}
