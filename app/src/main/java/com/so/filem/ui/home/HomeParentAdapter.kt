package com.so.filem.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.so.filem.R
import com.so.filem.databinding.ItemHomeHeaderBinding
import com.so.filem.databinding.ItemHomePopularPeopleBinding
import com.so.filem.databinding.ItemHomeTrendingBinding
import com.so.filem.ui.custom.Converter
import com.so.filem.ui.detail.cast.DetailCastActivity
import com.so.filem.ui.detail.movie.DetailMovieActivity
import com.so.filem.ui.detail.tv.DetailTvShowActivity
import timber.log.Timber

class HomeParentAdapter(
    private val homeItemList: List<HomeItem>,
    private val fragmentActivity: FragmentActivity
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /* inner class ParentViewHolder(private val binding: ItemHomeTrendingBinding) :
         RecyclerView.ViewHolder(binding.root) {
         fun bind(parentItem: HomeItem) {

             binding.ivTrending.setImageResource(parentItem.image)
             binding.tvHomeTitle.text = parentItem.title
             val titles = parentItem.titleTab
             val mediaType = parentItem.mediaType
             val sectionsPagerAdapter = HomeSectionsPagerAdapter( fragmentActivity, titles , mediaType )
             val viewPager = binding.vpHome
             viewPager.adapter = sectionsPagerAdapter
             viewPager.isUserInputEnabled = false
             val tabs: TabLayout = binding.tlHome
             TabLayoutMediator(tabs, viewPager) { tab, position ->
                 tab.text = titles[position]
             }.attach()
         }
     }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_TYPE_HEADER_MOVIE -> {
                HomeHeaderMovieItemViewHolder(
                    ItemHomeHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            HOME_TYPE_TRENDING_MOVIE -> {
                HomeTrendingMovieItemViewHolder(
                    ItemHomeTrendingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), fragmentActivity
                )
            }
            HOME_TYPE_HEADER_TV -> {
                HomeHeaderTvItemViewHolder(
                    ItemHomeHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            HOME_TYPE_TRENDING_TV -> {
                HomeTrendingTvItemViewHolder(
                    ItemHomeTrendingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), fragmentActivity
                )
            }
            else -> {
                HomePopularPeopleItemViewHolder(
                    ItemHomePopularPeopleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
        /*  val binding =
              ItemHomeTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
          return ParentViewHolder(binding)*/
    }

    override fun getItemCount(): Int {
        return homeItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return homeItemList[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeHeaderMovieItemViewHolder -> holder.bind(homeItemList[position] as HomeItem.HomeHeaderMovieItem)
            is HomeTrendingMovieItemViewHolder -> holder.bind(homeItemList[position] as HomeItem.HomeTrendingMovieItem)
            is HomeHeaderTvItemViewHolder -> holder.bind(homeItemList[position] as HomeItem.HomeHeaderTvShowItem)
            is HomeTrendingTvItemViewHolder -> holder.bind(homeItemList[position] as HomeItem.HomeTrendingTvShowItem)
            is HomePopularPeopleItemViewHolder -> holder.bind(homeItemList[position] as HomeItem.HomePopularPeopleItem)
        }
    }
}

class HomeHeaderMovieItemViewHolder(private val binding: ItemHomeHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(parentItem: HomeItem.HomeHeaderMovieItem) {
        binding.ivHomeHeaderPoster.load(parentItem.data.posterUrl){
            crossfade(true)
            placeholder(R.drawable.ic_placeholder_poster)
        }
        binding.tvHomeHeaderTitle.text = parentItem.data.title
        binding.tvHomeHeaderOverview.text = parentItem.data.overview
        binding.tvHomeHeaderRating.text = parentItem.data.vote_average?.let { Converter.roundOffDecimal(it) }
        itemView.setOnClickListener {
            DetailMovieActivity.startActivity(itemView.context,parentItem.data.id)
        }
    }

}

class HomeTrendingMovieItemViewHolder(
    private val binding: ItemHomeTrendingBinding,
    private val fragmentActivity: FragmentActivity
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(parentItem: HomeItem.HomeTrendingMovieItem) {
        binding.ivTrending.setImageResource(parentItem.image)
        binding.tvHomeTitle.text = itemView.context.getText(parentItem.title)
        val titles = parentItem.titleTab
        val mediaType = parentItem.mediaType
        val sectionsPagerAdapter = HomeSectionsPagerAdapter(fragmentActivity, titles, mediaType)
        val viewPager = binding.vpHome
        viewPager.adapter = sectionsPagerAdapter
        viewPager.isUserInputEnabled = false
        val tabs: TabLayout = binding.tlHome
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}

class HomeHeaderTvItemViewHolder(private val binding: ItemHomeHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(parentItem: HomeItem.HomeHeaderTvShowItem) {
        binding.ivHomeHeaderPoster.load(parentItem.data.posterUrl){
            crossfade(true)
            placeholder(R.drawable.ic_placeholder_poster)
        }
        binding.tvHomeHeaderTitle.text = parentItem.data.name
        binding.tvHomeHeaderOverview.text = parentItem.data.overview
        binding.tvHomeHeaderRating.text = parentItem.data.vote_average?.let { Converter.roundOffDecimal(it) }
        itemView.setOnClickListener {
            DetailTvShowActivity.startActivity(itemView.context,parentItem.data.id)
        }
    }

}

class HomeTrendingTvItemViewHolder(
    private val binding: ItemHomeTrendingBinding,
    private val fragmentActivity: FragmentActivity
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(parentItem: HomeItem.HomeTrendingTvShowItem) {
        binding.ivTrending.setImageResource(parentItem.image)
        binding.tvHomeTitle.text = itemView.context.getText(parentItem.title)
        val titles = parentItem.titleTab
        val mediaType = parentItem.mediaType
        val sectionsPagerAdapter = HomeSectionsPagerAdapter(fragmentActivity, titles, mediaType)
        val viewPager = binding.vpHome
        viewPager.adapter = sectionsPagerAdapter
        viewPager.isUserInputEnabled = false
        val tabs: TabLayout = binding.tlHome
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}

class HomePopularPeopleItemViewHolder(
    private val binding: ItemHomePopularPeopleBinding,
) :  RecyclerView.ViewHolder(binding.root) {

    private val adapter: HomeChildPopularPeopleAdapter by lazy {
        HomeChildPopularPeopleAdapter {
            DetailCastActivity.startActivity(itemView.context, it.id )
        }
    }

    fun bind(parentItem: HomeItem.HomePopularPeopleItem) {
        binding.ivPeople.setImageResource(parentItem.image)
        binding.tvHomeTitle.text =  itemView.context.getText(parentItem.title)
        Timber.tag("AdapterPoeple").d(parentItem.castList.toString())
        binding.rvHomePeople.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomePeople.adapter = adapter
        adapter.setItems(parentItem.castList)
    }
}