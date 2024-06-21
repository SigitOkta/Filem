package com.so.filem.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.so.filem.data.local.storepref.SettingPreferenceRepository
import com.so.filem.data.local.storepref.SettingPreferenceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingPreferenceModule {
    @Binds
    @Singleton
    abstract fun themePreferencesRepository(
        settingPreferenceRepositoryImpl: SettingPreferenceRepositoryImpl
    ): SettingPreferenceRepository

    companion object {

        // provides instance of DataStore
        @Provides
        @Singleton
        fun provideSettingPreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.dataStore
        }
    }
}