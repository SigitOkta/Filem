package com.so.filem.ui.thread

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.so.filem.R
import com.so.filem.base.BaseViewModelActivity
import com.so.filem.databinding.ActivityThreadBinding
import com.so.filem.domain.utils.WrapContentLinearLayoutManager
import com.so.filem.ui.adapter.ThreadListAdapter
import com.so.filem.ui.thread.threadform.ThreadFormBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ThreadActivity :
    BaseViewModelActivity<ActivityThreadBinding, ThreadViewModel>(ActivityThreadBinding::inflate) {

    @Inject
    lateinit var threadViewModelFactory: ThreadViewModel.ThreadViewModelFactory
    override val viewModel: ThreadViewModel by viewModels {
        ThreadViewModel.Factory(
            threadViewModelFactory,
            intent.extras ?: bundleOf()
        )
    }

    private val adapter: ThreadListAdapter by lazy {
        ThreadListAdapter(
            viewModel.getThreadStreamData(),
            viewModel.getCurrentUser(),
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

    override fun initView() {
        super.initView()
        binding.fabAdd.setOnClickListener {
            openCreateThreadBottomSheet()
        }
        binding.rvThread.layoutManager =
            WrapContentLinearLayoutManager(this@ThreadActivity)
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
        const val EXTRAS_TYPE_THREAD = "EXTRAS_TYPE_THREAD"
        fun startActivity(context: Context, id: String, mediaType: Int) {
            context.startActivity(Intent(context, ThreadActivity::class.java).apply {
                putExtra(EXTRAS_PARENT_THREAD, id)
                putExtra(EXTRAS_TYPE_THREAD, mediaType)
            })
        }
    }


    private fun openCreateThreadBottomSheet() {
        val id = intent.getStringExtra("EXTRAS_PARENT_THREAD")
        val mediaType = intent.getIntExtra("EXTRAS_TYPE_THREAD",0)
        if (id != null) {
            val bottomSheetDialogFragment = ThreadFormBottomSheet.newInstance(id, mediaType)
            bottomSheetDialogFragment.show(supportFragmentManager, null)
        }
    }
}