package com.so.filem.ui.thread.threadform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.data.repository.UserRepository
import com.so.filem.domain.repository.ThreadRepository
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadFormViewModel @Inject constructor(
    private val threadRepository: ThreadRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val createThreadResult = MutableLiveData<Resource<Boolean>>()

    fun createThread(movieId: String, title: String, content: String) {
        createThreadResult.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            createThreadResult.postValue(
                Resource.Success(threadRepository.createThread(generateThreadItem(movieId,title,content)))
            )
        }
    }

    private fun generateThreadItem(movieId: String,title: String, content: String) : ThreadItem {
        return ThreadItem(
            creator = userRepository.getCurrentUser(),
            title = title,
            movieId = movieId,
            content = content,
            members = mutableListOf(userRepository.getCurrentUser()),
            isMember = true
        )
    }

}