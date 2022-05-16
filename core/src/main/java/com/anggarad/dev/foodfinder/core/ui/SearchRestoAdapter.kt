package com.anggarad.dev.foodfinder.core.ui

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemLayoutSearchRestoBinding
import com.anggarad.dev.foodfinder.core.domain.model.SearchModel
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class SearchRestoAdapter : RecyclerView.Adapter<SearchRestoAdapter.SearchViewHolder>() {

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
        private var binding = ItemLayoutSearchRestoBinding.bind(itemView)
        fun bind(itemSearch: SearchModel) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(SERVER_URL + "uploads/${itemSearch.imgCover}")
                    .into(ivItemSearchResto)
                tvSearchRestoTitle.text = itemSearch.name
                tvSearchRestoLocation.text = "${itemSearch.distance} Km"
                tvSearchRestoReview.text = itemSearch.ratingAvg.toString()
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
    ): SearchRestoAdapter.SearchViewHolder = SearchViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_search_resto, parent, false)
    )

    override fun onBindViewHolder(holder: SearchRestoAdapter.SearchViewHolder, position: Int) {
        val data = listSearch[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listSearch.size
    }
}