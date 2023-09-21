package com.so.filem.ui.detail.cast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.so.filem.databinding.ItemListCastInfoBinding

class CastAdapter(private val context: Context, private val data: List<InfoCastContent>) : BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemListCastInfoBinding

        if (convertView == null) {
            binding = ItemListCastInfoBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemListCastInfoBinding
        }

        val currentItem = getItem(position) as InfoCastContent

        binding.tvCastInfoTitle.text = currentItem.title
        binding.tvCastInfoSubtitle.text = currentItem.dataInfo

        return binding.root
    }

}
