package com.remindermatrix.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.remindermatrix.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class TaskRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var taskDao: TaskDao
    private lateinit var groupDao: GroupDao
    private lateinit var sectionDao: SectionDao
    private lateinit var repository: TaskRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        taskDao = database.taskDao()
        groupDao = database.groupDao()
        sectionDao = database.sectionDao()
        repository = TaskRepository(taskDao, groupDao, sectionDao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `test insert and get task`() = runBlocking {
        val task = Task(
            title = "Test Task",
            dueDate = LocalDateTime.now(),
            groupId = "1",
            sectionId = "1",
            createdAt = LocalDateTime.now()
        )
        repository.upsertTask(task)

        val allTasks = repository.allTasks().first()
        assert(allTasks.contains(task))
    }
}
