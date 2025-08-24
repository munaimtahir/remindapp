package com.remindermatrix.ui.inbox

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.remindermatrix.data.Group
import com.remindermatrix.data.Section
import com.remindermatrix.data.TaskCategory

import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskSheet(
    onAddTask: (String, TaskCategory, String, String, LocalDateTime) -> Unit,
    viewModel: MainViewModel
) {
    var title by remember { mutableStateOf("") }
    val groups by viewModel.groups.collectAsState()
    val sections by viewModel.sections.collectAsState()

    var selectedGroup by remember { mutableStateOf<Group?>(null) }
    var selectedSection by remember { mutableStateOf<Section?>(null) }
    var selectedCategory by remember { mutableStateOf(TaskCategory.CAT2) }

    var isGroupExpanded by remember { mutableStateOf(false) }
    var isSectionExpanded by remember { mutableStateOf(false) }
    var isCategoryExpanded by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var isDatePickerOpen by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }

    // Effects to update selection when the list loads
    LaunchedEffect(groups) {
        if (selectedGroup == null) {
            selectedGroup = groups.firstOrNull()
        }
    }
    LaunchedEffect(sections) {
        if (selectedSection == null) {
            selectedSection = sections.firstOrNull()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { isDatePickerOpen = true }) {
            Text("Due Date: ${selectedDate.toLocalDate()}")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown for Groups
        ExposedDropdownMenuBox(
            expanded = isGroupExpanded,
            onExpandedChange = { isGroupExpanded = !isGroupExpanded }
        ) {
            OutlinedTextField(
                value = selectedGroup?.name ?: "Select Group",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGroupExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isGroupExpanded,
                onDismissRequest = { isGroupExpanded = false }
            ) {
                groups.forEach { group ->
                    DropdownMenuItem(
                        text = { Text(group.name) },
                        onClick = {
                            selectedGroup = group
                            isGroupExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown for Sections
        ExposedDropdownMenuBox(
            expanded = isSectionExpanded,
            onExpandedChange = { isSectionExpanded = !isSectionExpanded }
        ) {
            OutlinedTextField(
                value = selectedSection?.name ?: "Select Section",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSectionExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isSectionExpanded,
                onDismissRequest = { isSectionExpanded = false }
            ) {
                sections.forEach { section ->
                    DropdownMenuItem(
                        text = { Text(section.name) },
                        onClick = {
                            selectedSection = section
                            isSectionExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown for Category
        ExposedDropdownMenuBox(
            expanded = isCategoryExpanded,
            onExpandedChange = { isCategoryExpanded = !isCategoryExpanded }
        ) {
            OutlinedTextField(
                value = selectedCategory.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isCategoryExpanded,
                onDismissRequest = { isCategoryExpanded = false }
            ) {
                TaskCategory.values().forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category
                            isCategoryExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && selectedGroup != null && selectedSection != null) {
                    onAddTask(title, selectedCategory, selectedGroup!!.id, selectedSection!!.id, selectedDate)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
    }

    if (isDatePickerOpen) {
        DatePickerDialog(
            onDismissRequest = { isDatePickerOpen = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        isDatePickerOpen = false
                        selectedDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isDatePickerOpen = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
