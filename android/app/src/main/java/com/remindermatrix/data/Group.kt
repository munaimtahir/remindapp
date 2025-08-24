package com.remindermatrix.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String
)
