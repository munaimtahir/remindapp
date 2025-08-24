package com.remindermatrix.di

import com.remindermatrix.data.settings.SettingsLocalDataSource
import com.remindermatrix.repo.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(localDataSource: SettingsLocalDataSource): SettingsRepository {
        return SettingsRepository(localDataSource)
    }
}
