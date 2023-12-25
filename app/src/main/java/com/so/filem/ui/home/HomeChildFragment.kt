package com.so.filem.ui.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.so.filem.databinding.FragmentHomeChildBinding
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.TvShow
import com.so.filem.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeChildFragment : BaseViewModelFragment<FragmentHomeChildBinding, HomeViewModel>(
    FragmentHomeChildBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    companion object {
        const val ARG_SECTION_TITLE = "section_title"
        const val ARG_SECTION_MEDIA_TYPE = "section_media_Type"
    }

    override fun observeData() {
        super.observeData()
        viewModel.trendingMovie.observe(viewLifecycleOwner) {
           setupRvHomeMovie(it.results)
        }
        viewModel.trendingTv.observe(viewLifecycleOwner){
            setupRvHomeTv(it.results)
        }
    }

    override fun initView() {
        super.initView()
        val title = arguments?.getString(ARG_SECTION_TITLE)
        val mediaType = arguments?.getString(ARG_SECTION_MEDIA_TYPE)
        if (mediaType == "movie") {
            setupMovie(title)
        } else if (mediaType == "tv") {
            setupTv(title)
        }
    }

    private fun setupTv(title: String?) {
        if (title != null) {
            viewModel.setTrendingTv(title)
        }
    }

    private fun setupMovie(title: String?) {
        if (title != null) {
            viewModel.setTrendingMovie(title)
        }
    }

    private fun setupRvHomeMovie(results: List<Movie>) {
        viewBinding().rvHomeChild.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = HomeChildMovieAdapter(results)
        viewBinding().rvHomeChild.adapter = adapter
    }

    private fun setupRvHomeTv(results: List<TvShow>) {
        viewBinding().rvHomeChild.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = HomeChildTvAdapter(results)
        viewBinding().rvHomeChild.adapter = adapter
    }
}
