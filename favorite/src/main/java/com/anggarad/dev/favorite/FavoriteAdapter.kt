package com.anggarad.dev.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.favorite.databinding.ItemLayoutTrendBinding
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail

import com.bumptech.glide.Glide

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var listFavorite = ArrayList<RestoDetail>()
    var onItemClick: ((RestoDetail) -> Unit)? = null

    fun setFavoriteList(newList: List<RestoDetail>?) {
        if (newList == null) return
        listFavorite.clear()
        listFavorite.addAll(newList)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLayoutTrendBinding.bind(itemView)
        fun bind(data: RestoDetail) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("http://10.0.2.2/uploads/${data.imgMenuPath}")
                    .into(ivItemTrend)
                cafeHomeTitle.text = data.name
                cafeHomeSubtitle.text = data.address
                trendingReview.text = data.priceRange
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listFavorite[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_trend, parent, false)
        )


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val data = listFavorite[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }
}