package com.remindermatrix.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAtIso DESC")
    fun all(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(task: Task)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun delete(id: String)
}
