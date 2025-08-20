package com.remindermatrix.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

enum class TaskCategory { CAT1, CAT2, CAT3, CAT4 }
enum class TaskStatus { PENDING, COMPLETED, SNOOZED }

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val dueDateIso: String,
    val category: TaskCategory = TaskCategory.CAT2,
    val groupId: String,
    val sectionId: String,
    val status: TaskStatus = TaskStatus.PENDING,
    val createdAtIso: String
)
