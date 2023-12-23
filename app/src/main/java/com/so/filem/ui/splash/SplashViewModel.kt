package com.so.filem.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.so.filem.data.firebase.User
import com.so.filem.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    val currentUserLiveData = MutableLiveData<User?>()

    fun getCurrentUser() {
        currentUserLiveData.postValue(userRepository.getCurrentUser())
    }
}