/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 6/21/24, 3:21 PM
 */

package com.so.filem.data.local.storepref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingPreferenceRepository {
    fun getThemeSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}

class SettingPreferenceRepositoryImpl @Inject constructor(
    private val settingPreferences: DataStore<Preferences>
) : SettingPreferenceRepository {
    override fun getThemeSetting(): Flow<Boolean> {
        return settingPreferences.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreferences.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    private companion object {
        val THEME_KEY = booleanPreferencesKey("theme_setting")
    }

}