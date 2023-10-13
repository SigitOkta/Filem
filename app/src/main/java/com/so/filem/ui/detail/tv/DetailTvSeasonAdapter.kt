package com.so.filem.ui.detail.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemCastPosterBinding
import com.so.filem.databinding.ItemSeasonInfoBinding
import com.so.filem.domain.model.Cast
import com.so.filem.domain.model.Season
import com.so.filem.ui.custom.Converter
import com.so.filem.ui.detail.cast.DetailCastActivity
import timber.log.Timber

class DetailTvSeasonAdapter(private val seasons: List<Season>) : RecyclerView.Adapter<DetailTvSeasonAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemSeasonInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Season){
            binding.apply {
                ivPosterSeason.load(item.profileImageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_poster)
                }
                tvNameSeason.text = item.name
                tvEpisode.text = item.episodeCount.toString()
                tvYear.text = item.airDate?.let { Converter.splitYear(it) }
                tvRating.text = item.voteAverage.toString()
                tvSynopsisSeason.text = item.overview
            }

            /*itemView.setOnClickListener {
                DetailCastActivity.startActivity(itemView.context,item.id)
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSeasonInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return seasons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(seasons[position])
    }

}