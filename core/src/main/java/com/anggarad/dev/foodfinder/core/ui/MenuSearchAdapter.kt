package com.anggarad.dev.foodfinder.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemMenuCardSearchBinding
import com.anggarad.dev.foodfinder.core.domain.model.MenuResto
import com.bumptech.glide.Glide

class MenuSearchAdapter : RecyclerView.Adapter<MenuSearchAdapter.MenuViewHolder>() {

    private var listMenu = ArrayList<MenuResto>()
    var onItemClick: ((MenuResto) -> Unit)? = null

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    fun setMenuSearchList(newList: List<MenuResto>?) {
        if (newList == null) return
        listMenu.clear()
        listMenu.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemMenuCardSearchBinding.bind(itemView)
        fun bind(itemMenu: MenuResto) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(SERVER_URL + "uploads/${itemMenu.menuImg}")
                    .placeholder(R.drawable.ic_image)
                    .into(ivItemImage)
                tvItemTitle.text = itemMenu.menuName
                tvItemPrice.text = "Rp ${itemMenu.menuPrice}"
                if (itemMenu.isRecommended == 1) {
                    tvIsRecommendedMenu.visibility = View.VISIBLE
                } else {
                    tvIsRecommendedMenu.visibility = View.GONE
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listMenu[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = MenuViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_menu_card_search, parent, false)
    )

    override fun onBindViewHolder(holder: MenuSearchAdapter.MenuViewHolder, position: Int) {
        val data = listMenu[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }
}