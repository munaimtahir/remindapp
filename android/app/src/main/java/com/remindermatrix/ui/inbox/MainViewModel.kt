package com.remindermatrix.ui.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remindermatrix.data.*
import com.remindermatrix.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    getGroupsUseCase: GetGroupsUseCase,
    getSectionsUseCase: GetSectionsUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = getTasksUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val groups: StateFlow<List<Group>> = getGroupsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sections: StateFlow<List<Section>> = getSectionsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createTask(
        title: String,
        description: String? = null,
        dueDate: LocalDateTime,
        category: TaskCategory,
        groupId: String,
        sectionId: String
    ) {
        viewModelScope.launch {
            val task = Task(
                title = title,
                description = description,
                dueDate = dueDate,
                category = category,
                groupId = groupId,
                sectionId = sectionId,
                createdAt = LocalDateTime.now()
            )
            createTaskUseCase(task)
        }
    }

    fun updateTaskStatus(task: Task, status: TaskStatus) {
        viewModelScope.launch {
            updateTaskStatusUseCase(task, status)
        }
    }
}
