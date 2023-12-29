package com.so.filem.ui.thread.threaddetail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.so.filem.data.firebase.SubThreadItem
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.data.repository.UserRepository
import com.so.filem.domain.repository.ThreadRepository
import com.so.filem.domain.utils.Resource
import com.so.filem.domain.utils.parcelable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThreadDetailViewModel @AssistedInject constructor(
    private val threadRepository: ThreadRepository,
    private val userRepository: UserRepository,
    @Assisted private val intentData: Bundle
) : ViewModel() {

    @AssistedFactory
    interface ThreadDetailViewModelFactory  {
        fun create(intentData: Bundle): ThreadDetailViewModel
    }

    class Factory(
        private val assistedFactory: ThreadDetailViewModelFactory,
        private val intentData: Bundle,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(intentData) as T
        }
    }

    val parentThread: ThreadItem? by lazy {
        intentData.parcelable(ThreadDetailActivity.EXTRAS_THREAD)
    }

    val replyThreadResult = MutableLiveData<Resource<Boolean>>()

    fun replyThread(content: String) {
        replyThreadResult.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            replyThreadResult.postValue(
                Resource.Success(
                    threadRepository.createSubThread(
                        parentThread?.id.orEmpty(),
                        generateSubThread(content)
                    )
                )
            )
        }
    }

    private fun generateSubThread(content: String): SubThreadItem {
        return SubThreadItem(message = content, sender = userRepository.getCurrentUser())
    }

    fun getCurrentUser() = userRepository.getCurrentUser()

    fun getSubThread() = threadRepository.getSubThread(parentThread?.id.orEmpty())
}