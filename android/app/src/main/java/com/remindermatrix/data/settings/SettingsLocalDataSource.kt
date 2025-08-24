package com.remindermatrix.data.settings

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsLocalDataSource @Inject constructor(@ApplicationContext context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secret_shared_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Example of how to store and retrieve a setting
    fun setWeeklyDigestHour(hour: Int) {
        with(sharedPreferences.edit()) {
            putInt("KEY_WEEKLY_DIGEST_HOUR", hour)
            apply()
        }
    }

    fun getWeeklyDigestHour(): Int {
        return sharedPreferences.getInt("KEY_WEEKLY_DIGEST_HOUR", 17) // Default to 17:00 (5 PM)
    }
}
