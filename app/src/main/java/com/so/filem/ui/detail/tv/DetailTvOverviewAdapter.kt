package com.so.filem.ui.detail.tv

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.so.filem.databinding.CompDetailMovieCastBinding
import com.so.filem.databinding.CompDetailMovieVideoBinding
import com.so.filem.domain.model.Trailer
import com.so.filem.ui.adapter.CastAdapter
import com.so.filem.ui.adapter.TrailerAdapter

class DetailTvOverviewAdapter(
    private val overViewItemList: List<OverViewDetailTvItem>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DETAIL_TV_CAST -> {
                DetailTvCastViewHolder(
                    CompDetailMovieCastBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                DetailTvVideoViewHolder(
                    CompDetailMovieVideoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return overViewItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return overViewItemList[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailTvCastViewHolder -> holder.bind(overViewItemList[position] as OverViewDetailTvItem.DetailTvCast)
            is DetailTvVideoViewHolder -> holder.bind(overViewItemList[position] as OverViewDetailTvItem.DetailTvTrailer)
        }
    }

}

class DetailTvCastViewHolder(private val binding: CompDetailMovieCastBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(parentItem: OverViewDetailTvItem.DetailTvCast){
        if (parentItem.castList.isNotEmpty()){
            binding.tvCast.text = itemView.context.getText(parentItem.title)
            val rvCast = binding.rvCast
            rvCast.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            val castAdapter = CastAdapter(parentItem.castList)
            rvCast.adapter = castAdapter
        } else {
            binding.tvCast.visibility = View.GONE
        }
    }

}

class DetailTvVideoViewHolder(private val binding: CompDetailMovieVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(parentItem: OverViewDetailTvItem.DetailTvTrailer) {
        if (parentItem.trailer.isNotEmpty()){
            binding.tvTrailer.text = itemView.context.getText(parentItem.title)
            val rvTrailer = binding.rvTrailer
            rvTrailer.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            val trailerAdapter = TrailerAdapter(parentItem.trailer){ trailer ->
                playVideo(trailer, itemView.context)
            }
            rvTrailer.adapter = trailerAdapter
        } else {
            binding.tvTrailer.visibility = View.GONE
        }
    }

    private fun playVideo(trailer: Trailer?, context: Context) {
        if (trailer?.key != null) {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trailer.youTubeAppUrl))
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trailer.youTubeWebUrl))

            if (appIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(appIntent)
            } else {
                context.startActivity(webIntent)
            }
        }
    }

}

