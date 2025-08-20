package com.remindermatrix.repo

import com.remindermatrix.data.Task
import com.remindermatrix.data.TaskDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val dao: TaskDao
) {
    fun all(): Flow<List<Task>> = dao.all()
    suspend fun upsert(task: Task) = dao.upsert(task)
    suspend fun delete(id: String) = dao.delete(id)
}
