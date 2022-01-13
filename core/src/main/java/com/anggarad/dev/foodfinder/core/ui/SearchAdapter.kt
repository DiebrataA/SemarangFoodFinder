package com.anggarad.dev.foodfinder.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.data.source.remote.response.SearchItem
import com.anggarad.dev.foodfinder.core.databinding.ItemLayoutSearchBinding
import com.bumptech.glide.Glide

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var listSearch = ArrayList<SearchItem>()
    var onItemClick: ((SearchItem) -> Unit)? = null

    fun setSearchList(newList: List<SearchItem>?) {
        if (newList == null) return
        listSearch.clear()
        listSearch.addAll(newList)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemLayoutSearchBinding.bind(itemView)
        fun bind(itemSearch: SearchItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("http://192.168.1.3:4000/uploads/${itemSearch.imgCover}")
                    .into(ivSearchResto)
                tvSearchRestoTitle.text = itemSearch.name
                tvSearchLocationResto.text = itemSearch.location
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listSearch[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder = SearchViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout_search, parent, false)
    )

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val data = listSearch[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listSearch.size
    }
}