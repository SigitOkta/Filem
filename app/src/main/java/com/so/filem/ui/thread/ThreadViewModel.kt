package com.so.filem.ui.thread

import androidx.lifecycle.ViewModel
import com.so.filem.domain.repository.ThreadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val repository: ThreadRepository
) : ViewModel() {
    fun getThreadStreamData() = repository.getThread()
}