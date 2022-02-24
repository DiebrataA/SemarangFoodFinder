package com.anggarad.dev.foodfinder.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemCategoriesBinding
import com.anggarad.dev.foodfinder.core.domain.model.CategoriesDetail
import com.bumptech.glide.Glide

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var listCategories = ArrayList<CategoriesDetail>()
    var onItemClick: ((CategoriesDetail) -> Unit)? = null

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    fun setCategoriesList(newList: List<CategoriesDetail>?) {
        if (newList == null) return
        listCategories.clear()
        listCategories.addAll(newList)
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemCategoriesBinding.bind(itemView)
        fun bind(itemCategories: CategoriesDetail) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(SERVER_URL + "uploads/${itemCategories.categoryImg}")
                    .into(ivCategories)
                tvCategoriesName.text = itemCategories.categoryName
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listCategories[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_categories, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val data = listCategories[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listCategories.size
    }
}