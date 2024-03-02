/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.thread.threaddetail

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.so.filem.R
import com.so.filem.base.BaseViewModelActivity
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.databinding.ActivityThreadDetailBinding
import com.so.filem.domain.utils.ButtonEnabledTextWatcher
import com.so.filem.domain.utils.OnReplyScrollObserver
import com.so.filem.domain.utils.Resource
import com.so.filem.ui.adapter.MessageAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ThreadDetailActivity :
    BaseViewModelActivity<ActivityThreadDetailBinding, ThreadDetailViewModel>(
        ActivityThreadDetailBinding::inflate
    ) {

    @Inject
    lateinit var threadDetailViewModelFactory: ThreadDetailViewModel.ThreadDetailViewModelFactory
    override val viewModel: ThreadDetailViewModel by viewModels {
        ThreadDetailViewModel.Factory(
            threadDetailViewModelFactory,
            intent.extras ?: bundleOf()
        )
    }

    private val adapter: MessageAdapter by lazy {
        MessageAdapter(
            viewModel.getSubThread(),
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
            }
        )
    }
    override fun onPause() {
        super.onPause()
        adapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.rvDetailThread.isVisible = !isShowLoading
        binding.tvErrorHome.isVisible = !isShowLoading
        binding.pbDetail.isVisible = isShowLoading
    }

    private fun showError() {
        binding.rvDetailThread.isVisible = false
        binding.pbDetail.isVisible = false
        binding.tvErrorHome.isVisible = true
    }

    private fun showData() {
        binding.rvDetailThread.isVisible = true
        binding.pbDetail.isVisible = false
        binding.tvErrorHome.isVisible = false
    }

    private fun showEmptyData() {
        showError()
        setErrorMessage(getText(R.string.text_empty_data_subthread).toString())
    }

    private fun setErrorMessage(msg: String) {
        binding.tvErrorHome.text = msg
    }
    override fun initView() {
        super.initView()
        checkIfCurrentUserIsCreator()
        checkIfCurrentUserInList()
        setParentThreadData(item = viewModel.parentThread)
        setupReply()
        setupRecyclerView()
        binding.llAddMember.setOnClickListener {
            viewModel.addMember()
            binding.llAddSubthread.visibility = View.VISIBLE
            binding.llAddMember.visibility = View.GONE
        }
    }

    private fun checkIfCurrentUserIsCreator() {
        viewModel.checkIfCurrentUserIsCreator()
    }

    private fun checkIfCurrentUserInList() {
        viewModel.checkIfCurrentUserIsMember()
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.rvDetailThread.layoutManager = manager
        adapter.registerAdapterDataObserver(
            OnReplyScrollObserver(
                binding.rvDetailThread,
                adapter,
                manager
            )
        )
        binding.rvDetailThread.adapter = adapter
    }

    override fun observeData() {
        super.observeData()
        viewModel.replyThreadResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    //nothing
                }
                is Resource.Error -> {
                    binding.pbDetail.isVisible = false
                    binding.etSubThread.isEnabled = true
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.etSubThread.isEnabled = false
                    binding.pbDetail.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbDetail.isVisible = false
                    binding.etSubThread.isEnabled = true
                    binding.etSubThread.setText("")
                    Toast.makeText(this, "Reply Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.isCurrentUserInCreator.observe(this){
            Timber.tag("ThreadDetail").d(it.toString())
            when (it){
                true ->{
                    binding.llAddSubthread.visibility = View.VISIBLE
                    binding.llAddMember.visibility = View.GONE
                    viewModel.isCurrentUserInMember.observe(this){ member ->
                       if(!member) viewModel.addMember()
                    }
                }
                false -> {
                    viewModel.isCurrentUserInMember.observe(this){ member ->
                        when (member){
                            true ->{
                                binding.llAddSubthread.visibility = View.VISIBLE
                                binding.llAddMember.visibility = View.GONE
                            }
                            false -> {
                                binding.llAddSubthread.visibility = View.GONE
                                binding.llAddMember.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }

    }

    private fun setupReply() {
        binding.etSubThread.apply {
            addTextChangedListener(
                ButtonEnabledTextWatcher(
                    this@ThreadDetailActivity,
                    binding.btnSendSubThread
                )
            )

        }
        binding.btnSendSubThread.setOnClickListener { sendSubThread() }
    }

    private fun sendSubThread() {
        val content = binding.etSubThread.text.toString().trim()
        viewModel.replyThread(content)
    }

    private fun setParentThreadData(item: ThreadItem?) {
        item?.let {
            binding.ivProfilePict.load(item.creator?.photoProfileUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_person)
                error(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
            binding.tvTitleThread.text = item.title
            binding.tvContentThread.text = item.content
            binding.tvNameThreadStarter.text = getString(
                R.string.text_container_display_creator_thread,
                item.creator?.displayName
            )

        }
    }

    companion object {
        const val EXTRAS_THREAD = "EXTRAS_THREAD"
        fun startActivity(context: Context, parentThread: ThreadItem) {
            context.startActivity(Intent(context, ThreadDetailActivity::class.java).apply {
                putExtra(EXTRAS_THREAD, parentThread)
            })
        }
    }
}