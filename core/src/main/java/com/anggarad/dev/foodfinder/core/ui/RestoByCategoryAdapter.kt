package com.anggarad.dev.foodfinder.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemLayoutBinding
import com.anggarad.dev.foodfinder.core.domain.model.RestoByCategoryDetail
import com.bumptech.glide.Glide

class RestoByCategoryAdapter : RecyclerView.Adapter<RestoByCategoryAdapter.RestoViewHolder>() {

    private var listResto = ArrayList<RestoByCategoryDetail>()
    var onItemClick: ((RestoByCategoryDetail) -> Unit)? = null

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    fun setRestoList(newList: List<RestoByCategoryDetail>?) {
        if (newList == null) return
        listResto.clear()
        listResto.addAll(newList)
        notifyDataSetChanged()
    }

    inner class RestoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemLayoutBinding.bind(itemView)
        fun bind(itemResto: RestoByCategoryDetail) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(SERVER_URL + "uploads/${itemResto.imgCover}")
                    .into(ivItemImage)
                tvItemRating.text = itemResto.ratingAvg.toString()
                tvItemTitle.text = itemResto.name
                tvItemApproxPrice.text = "Approx. Rp ${itemResto.priceRange} for two"
                tvItemCategories.text =
                    itemResto.categories.toString().replace("[", "").replace("]", "")
                tvItemLocation.text = itemResto.location
                if (itemResto.isHalal != 1) {
                    tvItemIsHalal.visibility = View.GONE
                } else {
                    tvItemIsHalal.visibility = View.VISIBLE
                    tvItemIsHalal.text = "Halal"
                }
                if (itemResto.haveInternet != 1) {
                    icWifi.visibility = View.GONE
                } else if (itemResto.haveToilet != 1) {
                    icWc.visibility = View.GONE
                } else if (itemResto.haveSocket != 1) {
                    icSocket.visibility = View.GONE
                } else {
                    icWifi.visibility = View.VISIBLE
                    icSocket.visibility = View.VISIBLE
                    icWc.visibility = View.VISIBLE
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listResto[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RestoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
    )

    override fun onBindViewHolder(holder: RestoByCategoryAdapter.RestoViewHolder, position: Int) {
        val data = listResto[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listResto.size
    }
}