package com.anggarad.dev.foodfinder.core.ui

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemLayoutSearchMenuBinding
import com.anggarad.dev.foodfinder.core.domain.model.SearchModel
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var listSearch = ArrayList<SearchModel>()
    var onItemClick: ((SearchModel) -> Unit)? = null

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    fun setSearchList(newList: List<SearchModel>?, userLat: Double, userLng: Double) {
        if (newList == null) return
        listSearch.clear()
        for (item in newList) {
            val userLocation = Location("userLocation")
            userLocation.longitude = userLng
            userLocation.latitude = userLat
            val restoLocation = Location("restoLocation")
            restoLocation.latitude = item.latitude
            restoLocation.longitude = item.longitude
            val distance = userLocation.distanceTo(restoLocation)
            item.distance = (distance / 1000 * 10.0).roundToInt() / 10.0f
            listSearch.add(item)
        }
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemLayoutSearchMenuBinding.bind(itemView)
        fun bind(itemSearch: SearchModel) {
            val childAdapter = MenuSearchAdapter()
            with(binding) {
                Glide.with(itemView.context)
                    .load(SERVER_URL + "uploads/${itemSearch.imgCover}")
                    .into(ivItemImage)
                tvItemTitle.text = itemSearch.name
                tvItemLocation.text = "${itemSearch.distance} Km"
                tvDishesFound.text = "${itemSearch.menu?.size} Dishes Found"
                tvItemRating.text = itemSearch.ratingAvg.toString()
                if (itemSearch.isHalal == 1) {
                    tvItemIsHalal.visibility = View.VISIBLE
                } else {
                    tvItemIsHalal.visibility = View.INVISIBLE
                }
                childAdapter.setMenuSearchList(itemSearch.menu)
                rvMenuSearch.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                rvMenuSearch.adapter = childAdapter
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
        viewType: Int,
    ): SearchAdapter.SearchViewHolder = SearchViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout_search_menu, parent, false)
    )

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        val data = listSearch[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listSearch.size
    }
}