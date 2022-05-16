package com.anggarad.dev.foodfinder.core.ui

import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemLayoutCafeHomeBinding
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class CafeAdapter : RecyclerView.Adapter<CafeAdapter.CafeViewHolder>() {

    private var listCafe = ArrayList<RestoDetail>()
    var onItemClick: ((RestoDetail) -> Unit)? = null

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    fun setCafeList(newList: List<RestoDetail>?, userLat: Double, userLng: Double) {
        if (newList == null) return
        listCafe.clear()
        for (item in newList) {
            val userLocation = Location("userLocation")
            userLocation.longitude = userLng
            userLocation.latitude = userLat
            val restoLocation = Location("restoLocation")
            restoLocation.latitude = item.latitude
            restoLocation.longitude = item.longitude
            val distance = userLocation.distanceTo(restoLocation)
            item.distance = (distance / 1000 * 10.0).roundToInt() / 10.0f
            listCafe.add(item)
        }
        listCafe.sortByDescending { it.ratingAvg }
        notifyDataSetChanged()
    }

    inner class CafeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemLayoutCafeHomeBinding.bind(itemView)
        fun bind(itemCafe: RestoDetail) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(SERVER_URL + "uploads/${itemCafe.imgCover}")
                    .into(ivItemTrend)
                trendingReview.text = itemCafe.ratingAvg.toString()
                cafeHomeTitle.text = itemCafe.name
                cafeHomeSubtitle.text =
                    itemCafe.categories.toString().replace("[", "").replace("]", "")
                cafeHomeLocation.text = "${itemCafe.distance} Km"
                Log.d("Cafe Home Title :", itemCafe.name)
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listCafe[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeViewHolder {
        return CafeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout_cafe_home, parent, false))
    }

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        val data = listCafe[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listCafe.size
    }
}