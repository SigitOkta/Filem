package com.so.filem.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.data.firebase.User
import com.so.filem.data.repository.UserRepository
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val loginResult = MutableLiveData<Resource<Pair<Boolean, User?>>>()

    fun authenticateGoogleLogin(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginResult.postValue(Resource.Loading())
            loginResult.postValue(Resource.Success(userRepository.signInWithCredential(token)))
        }
    }

    fun doLogout() {
        userRepository.logoutUser()
    }
}