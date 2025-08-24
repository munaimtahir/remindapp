package com.remindermatrix.work

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.remindermatrix.data.Task
import com.remindermatrix.repo.TaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import androidx.work.workDataOf
import com.remindermatrix.data.TaskCategory
import kotlinx.coroutines.flow.flowOf
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ReminderWorkerTest {

    private lateinit var context: Context
    private lateinit var taskRepository: TaskRepository

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        taskRepository = mock()
    }

    @Test
    fun testReminderWorker_success() {
        val task = Task(
            id = "test-id",
            title = "Test Task",
            description = "Test Description",
            dueDate = LocalDateTime.now(),
            category = TaskCategory.CAT1,
            groupId = "group-1",
            sectionId = "section-1",
            createdAt = LocalDateTime.now()
        )
        runBlocking {
            whenever(taskRepository.allTasks()).thenReturn(flowOf(listOf(task)))
        }

        val worker = TestListenableWorkerBuilder<ReminderWorker>(
            context = context,
            inputData = workDataOf("TASK_ID" to "test-id")
        ).setWorkerFactory(TestWorkerFactory(taskRepository)).build()

        runBlocking {
            val result = worker.doWork()
            assertEquals(ListenableWorker.Result.success(), result)
        }
    }

    @Test
    fun testReminderWorker_taskNotFound_failure() {
        runBlocking {
            whenever(taskRepository.allTasks()).thenReturn(flowOf(emptyList()))
        }

        val worker = TestListenableWorkerBuilder<ReminderWorker>(
            context = context,
            inputData = workDataOf("TASK_ID" to "non-existent-id")
        ).setWorkerFactory(TestWorkerFactory(taskRepository)).build()

        runBlocking {
            val result = worker.doWork()
            assertEquals(ListenableWorker.Result.failure(), result)
        }
    }
}

class TestWorkerFactory(private val repository: TaskRepository) : androidx.work.WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: androidx.work.WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ReminderWorker::class.java.name -> ReminderWorker(appContext, workerParameters, repository)
            else -> null
        }
    }
}
