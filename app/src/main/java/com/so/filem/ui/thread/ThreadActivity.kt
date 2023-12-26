package com.so.filem.ui.thread

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.so.filem.R
import com.so.filem.base.BaseViewModelActivity
import com.so.filem.databinding.ActivityThreadBinding
import com.so.filem.ui.adapter.ThreadListAdapter
import com.so.filem.ui.thread.threadform.ThreadFormBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThreadActivity :
    BaseViewModelActivity<ActivityThreadBinding, ThreadViewModel>(ActivityThreadBinding::inflate) {
    override val viewModel: ThreadViewModel by viewModels()


    private val adapter: ThreadListAdapter by lazy {
        ThreadListAdapter(viewModel.getThreadStreamData(),
            onDataExist = {
                showData()
            },
            onLoading = {
                showLoading(it)
            },
            onDataError = {
                showError()
                setErrorMessage(it.message.orEmpty())
            },
            onDataEmpty = {
                showEmptyData()
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.fabAdd.setOnClickListener {
            openCreateThreadBottomSheet()
        }
        binding.rvThread.layoutManager =
            LinearLayoutManager(this@ThreadActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvThread.adapter = this.adapter
    }

    override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.apply {
            rvThread.isVisible = !isShowLoading
            tvErrorHome.isVisible = !isShowLoading
            pbHome.isVisible = isShowLoading
        }

    }

    private fun showError() {
        binding.apply {
            rvThread.isVisible = false
            pbHome.isVisible = false
            tvErrorHome.isVisible = true
        }
    }

    private fun showData() {
        binding.apply {
            rvThread.isVisible = true
            pbHome.isVisible = false
            tvErrorHome.isVisible = false
        }
    }

    private fun showEmptyData() {
        showError()
        setErrorMessage(getText(R.string.text_empty_data).toString())
    }

    private fun setErrorMessage(msg: String) {
        binding.tvErrorHome.text = msg
    }

    companion object {
        const val EXTRAS_PARENT_THREAD = "EXTRAS_PARENT_THREAD"
        fun startActivity(context: Context, movieId: String) {
            context.startActivity(Intent(context, ThreadActivity::class.java).apply {
                putExtra(EXTRAS_PARENT_THREAD, movieId)
            })
        }
    }


    private fun openCreateThreadBottomSheet() {
        val movieId = intent.getStringExtra("EXTRAS_PARENT_THREAD")
        if (movieId != null) {
            val bottomSheetDialogFragment = ThreadFormBottomSheet.newInstance(movieId)
            bottomSheetDialogFragment.show(supportFragmentManager, null)
        }
    }
}