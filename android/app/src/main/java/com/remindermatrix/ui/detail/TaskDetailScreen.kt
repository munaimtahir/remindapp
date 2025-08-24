package com.remindermatrix.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remindermatrix.ui.inbox.MainViewModel

@Composable
fun TaskDetailScreen(
    taskId: String,
    viewModel: MainViewModel = hiltViewModel()
) {
    // This is a placeholder.
    // In a real app, you would have a dedicated ViewModel for this screen
    // or a method in the MainViewModel to fetch a single task.
    val task = viewModel.tasks.value.find { it.id == taskId }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Task Details") }) }
    ) { padding ->
        if (task != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(text = task.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                task.description?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Category: ${task.category.name}")
                Text(text = "Status: ${task.status.name}")
                Text(text = "Due Date: ${task.dueDate}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.updateTaskStatus(task, com.remindermatrix.data.TaskStatus.COMPLETED)
                    },
                    enabled = task.status != com.remindermatrix.data.TaskStatus.COMPLETED
                ) {
                    Text("Mark as Completed")
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Task not found")
            }
        }
    }
}
