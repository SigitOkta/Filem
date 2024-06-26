/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 6/21/24, 2:56 PM
 */

package com.so.filem.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.so.filem.data.firebase.User
import com.so.filem.data.local.storepref.SettingPreferenceRepository
import com.so.filem.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val pref: SettingPreferenceRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    val currentUserLiveData = MutableLiveData<User?>()
    fun getCurrentUser() {
        currentUserLiveData.postValue(userRepository.getCurrentUser())
    }
    fun doLogout() {
        userRepository.logoutUser()
    }
}