package com.remindermatrix.ui.settings

import androidx.lifecycle.ViewModel
import com.remindermatrix.repo.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    fun getWeeklyDigestHour(): Int {
        return settingsRepository.getWeeklyDigestHour()
    }

    fun setWeeklyDigestHour(hour: Int) {
        settingsRepository.setWeeklyDigestHour(hour)
    }
}
