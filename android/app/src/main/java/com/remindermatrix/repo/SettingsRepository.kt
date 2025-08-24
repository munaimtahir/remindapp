package com.remindermatrix.repo

import com.remindermatrix.data.settings.SettingsLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val localDataSource: SettingsLocalDataSource
) {
    fun getWeeklyDigestHour(): Int {
        return localDataSource.getWeeklyDigestHour()
    }

    fun setWeeklyDigestHour(hour: Int) {
        localDataSource.setWeeklyDigestHour(hour)
    }
}
