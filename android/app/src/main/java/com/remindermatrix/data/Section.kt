package com.remindermatrix.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sections")
data class Section(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String
)
