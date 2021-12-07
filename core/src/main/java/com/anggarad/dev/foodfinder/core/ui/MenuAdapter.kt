package com.anggarad.dev.foodfinder.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemMenuPhotoListBinding
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.bumptech.glide.Glide

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var listMenu = ArrayList<RestoDetail>()
    var onItemClick: ((RestoDetail) -> Unit)? = null

    fun setMenuList(newList: List<RestoDetail>?) {
        if (newList == null) return
        listMenu.clear()
        listMenu.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemMenuPhotoListBinding.bind(itemView)
        fun bind(itemMenu: RestoDetail) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("http://192.168.1.5:4000/uploads/${itemMenu.imgMenuPath}")
                    .into(ivMenuPhoto)

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
        LayoutInflater.from(parent.context).inflate(R.layout.item_menu_photo_list, parent, false)
    )

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        val data = listMenu[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }
}