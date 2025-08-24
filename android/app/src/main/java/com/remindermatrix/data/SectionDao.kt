package com.remindermatrix.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {
    @Query("SELECT * FROM sections ORDER BY name ASC")
    fun all(): Flow<List<Section>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(sections: List<Section>)
}
