package com.remindermatrix.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Query("SELECT * FROM groups ORDER BY name ASC")
    fun all(): Flow<List<Group>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(groups: List<Group>)
}
