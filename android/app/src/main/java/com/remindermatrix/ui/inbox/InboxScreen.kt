package com.remindermatrix.ui.inbox

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remindermatrix.data.Task
import java.time.LocalDateTime

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onTaskClick: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reminder-Matrix") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isSheetOpen = true }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(tasks) { task ->
                TaskItem(task, onClick = { onTaskClick(task.id) })
            }
        }

        if (isSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { isSheetOpen = false },
                sheetState = sheetState
            ) {
                AddTaskSheet(
                    onAddTask = { title, category, groupId, sectionId, dueDate ->
                        viewModel.createTask(
                            title = title,
                            dueDate = dueDate,
                            category = category,
                            groupId = groupId,
                            sectionId = sectionId
                        )
                        isSheetOpen = false
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)
            task.description?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
