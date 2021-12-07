package com.anggarad.dev.foodfinder.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemLayoutBinding
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.bumptech.glide.Glide

class RestoAdapter : RecyclerView.Adapter<RestoAdapter.RestoViewHolder>() {

    private var listResto = ArrayList<RestoDetail>()
    var onItemClick: ((RestoDetail) -> Unit)? = null

    fun setRestoList(newList: List<RestoDetail>?) {
        if (newList == null) return
        listResto.clear()
        listResto.addAll(newList)
        notifyDataSetChanged()
    }

    inner class RestoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemLayoutBinding.bind(itemView)
        fun bind(itemResto: RestoDetail) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("http://192.168.1.5:4000/uploads/${itemResto.imgCover}")
                    .into(ivItemImage)
                tvItemTitle.text = itemResto.name
                tvItemApproxPrice.text = itemResto.priceRange
                if(itemResto.isHalal != 1) {
                    tvItemIsHalal.visibility = View.GONE
                } else {
                    tvItemIsHalal.visibility = View.VISIBLE
                    tvItemIsHalal.text = "Halal"
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
    ) = RestoViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
    )

    override fun onBindViewHolder(holder: RestoAdapter.RestoViewHolder, position: Int) {
        val data = listResto[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
       return listResto.size
    }
}