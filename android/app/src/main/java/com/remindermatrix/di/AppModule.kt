package com.remindermatrix.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.remindermatrix.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        groupDaoProvider: Provider<GroupDao>,
        sectionDaoProvider: Provider<SectionDao>
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "remindermatrix.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    groupDaoProvider.get().upsertAll(SampleData.GROUPS)
                    sectionDaoProvider.get().upsertAll(SampleData.SECTIONS)
                }
            }
        }).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao = appDatabase.taskDao()

    @Provides
    @Singleton
    fun provideGroupDao(appDatabase: AppDatabase): GroupDao = appDatabase.groupDao()

    @Provides
    @Singleton
    fun provideSectionDao(appDatabase: AppDatabase): SectionDao = appDatabase.sectionDao()
}
