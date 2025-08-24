package com.remindermatrix.repo

import com.remindermatrix.data.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val groupDao: GroupDao,
    private val sectionDao: SectionDao
) {
    fun allTasks(): Flow<List<Task>> = taskDao.all()
    suspend fun upsertTask(task: Task) = taskDao.upsert(task)
    suspend fun deleteTask(id: String) = taskDao.delete(id)

    fun allGroups(): Flow<List<Group>> = groupDao.all()
    fun allSections(): Flow<List<Section>> = sectionDao.all()
}
