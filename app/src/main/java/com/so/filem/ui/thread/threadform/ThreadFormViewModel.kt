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

    fun createThread(id: String, mediaType: Int, title: String, content: String) {
        createThreadResult.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            createThreadResult.postValue(
                Resource.Success(
                    threadRepository.createThread(
                        generateThreadItem(
                            id,
                            mediaType,
                            title,
                            content
                        )
                    )
                )
            )
        }
    }

    private fun generateThreadItem(
        id: String,
        mediaType: Int,
        title: String,
        content: String
    ): ThreadItem {
        return ThreadItem(
            creator = userRepository.getCurrentUser(),
            title = title,
            idMediaType = mediaType.toString()+"_"+ id,
            content = content,
            members = mutableListOf(userRepository.getCurrentUser()),
        )
    }

}