package com.remindermatrix.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class, Group::class, Section::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun groupDao(): GroupDao
    abstract fun sectionDao(): SectionDao
}
