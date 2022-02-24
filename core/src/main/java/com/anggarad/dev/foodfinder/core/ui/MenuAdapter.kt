package com.anggarad.dev.foodfinder.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemMenuCardBinding
import com.anggarad.dev.foodfinder.core.domain.model.MenuDetail
import com.bumptech.glide.Glide

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var listMenu = ArrayList<MenuDetail>()
    var onItemClick: ((MenuDetail) -> Unit)? = null

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    fun setMenuList(newList: List<MenuDetail>?) {
        if (newList == null) return
        listMenu.clear()
        listMenu.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemMenuCardBinding.bind(itemView)
        fun bind(itemMenu: MenuDetail) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(SERVER_URL + "uploads/${itemMenu.menuImg}")
                    .placeholder(R.drawable.ic_image)
                    .into(ivItemImage)
                tvItemTitle.text = itemMenu.menuName
                tvItemPrice.text = "Rp ${itemMenu.menuPrice}"
                if (itemMenu.isRecommended == 0 || itemMenu.isRecommended == null) {
                    tvItemIsBestSeller.visibility = View.GONE
                } else {
                    tvItemIsBestSeller.visibility = View.VISIBLE
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
        viewType: Int
    ) = MenuViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_menu_card, parent, false)
    )

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        val data = listMenu[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }
}