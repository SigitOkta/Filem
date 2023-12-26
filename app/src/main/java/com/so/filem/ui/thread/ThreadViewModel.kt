package com.so.filem.ui.thread

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.so.filem.domain.repository.ThreadRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ThreadViewModel @AssistedInject constructor(
    private val repository: ThreadRepository,
    @Assisted private val intentData: Bundle
) : ViewModel() {

    @AssistedFactory
    interface ThreadViewModelFactory  {
        fun create(intentData: Bundle): ThreadViewModel
    }

    class Factory(
        private val assistedFactory: ThreadViewModelFactory,
        private val intentData: Bundle,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(intentData) as T
        }
    }

    val movieId: String? by lazy {
        intentData.getString(ThreadActivity.EXTRAS_PARENT_THREAD)
    }
    fun getThreadStreamData() = repository.getThreadByMovieId(movieId ?: "")
}

