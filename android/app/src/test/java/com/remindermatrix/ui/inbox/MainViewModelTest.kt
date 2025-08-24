package com.remindermatrix.ui.inbox

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.remindermatrix.data.Task
import com.remindermatrix.data.TaskCategory
import com.remindermatrix.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var getTasksUseCase: GetTasksUseCase
    private lateinit var getGroupsUseCase: GetGroupsUseCase
    private lateinit var getSectionsUseCase: GetSectionsUseCase
    private lateinit var createTaskUseCase: CreateTaskUseCase
    private lateinit var updateTaskStatusUseCase: UpdateTaskStatusUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getTasksUseCase = mock()
        getGroupsUseCase = mock()
        getSectionsUseCase = mock()
        createTaskUseCase = mock()
        updateTaskStatusUseCase = mock()

        whenever(getTasksUseCase()).thenReturn(flowOf(emptyList()))
        whenever(getGroupsUseCase()).thenReturn(flowOf(emptyList()))
        whenever(getSectionsUseCase()).thenReturn(flowOf(emptyList()))

        viewModel = MainViewModel(
            getTasksUseCase,
            getGroupsUseCase,
            getSectionsUseCase,
            createTaskUseCase,
            updateTaskStatusUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test create task calls use case`() = runBlocking {
        val task = Task(
            title = "Test Task",
            dueDate = LocalDateTime.now(),
            category = TaskCategory.CAT1,
            groupId = "1",
            sectionId = "1",
            createdAt = LocalDateTime.now()
        )
        viewModel.createTask(
            task.title,
            task.description,
            task.dueDate,
            task.category,
            task.groupId,
            task.sectionId
        )
        // We can't verify the exact task object because the one created in the VM has a different timestamp and ID.
        // A better approach would be to have the use case return the created task.
        // For now, we'll just verify that the use case was called.
        verify(createTaskUseCase).invoke(org.mockito.kotlin.any())
    }
}
