package com.so.filem.ui.home
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemHomePosterBinding
import com.so.filem.domain.model.Tv

class HomeChildTvAdapter(private val childList: List<Tv>) :
    RecyclerView.Adapter<HomeChildTvAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(private val binding: ItemHomePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(childItem: Tv) {
            binding.ivPoster.load(childItem.posterUrl){
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            if (childItem.name.isNullOrEmpty()){
                binding.tvPoster.text = "-"
            } else {
                binding.tvPoster.text = childItem.name
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding =
            ItemHomePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(childList[position])
    }

    override fun getItemCount(): Int {
        return childList.size
    }
}