package com.so.filem.ui.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.so.filem.databinding.FragmentHomeChildBinding
import com.so.filem.domain.model.Movie
import com.so.filem.ui.base.BaseViewModelFragment
import com.so.filem.ui.detail.cast.MediaChildAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeChildFragment : BaseViewModelFragment<FragmentHomeChildBinding, HomeViewModel>(
    FragmentHomeChildBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    companion object {
        const val ARG_SECTION_TITLE = "section_title"
    }

    override fun observeData() {
        super.observeData()
        viewModel.trendingMovie.observe(viewLifecycleOwner) {
            setupRvHome(it.results)
        }
    }

    override fun initView() {
        super.initView()
        val title = arguments?.getString(ARG_SECTION_TITLE)
        Timber.tag("HomeChild").d(title)
        if (title != null) {
            viewModel.setTrending(title)
        }


    }

    private fun setupRvHome(results: List<Movie>) {
        viewBinding().rvHomeChild.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = HomeChildAdapter(results)
        viewBinding().rvHomeChild.adapter = adapter
    }
}
