package com.anggarad.dev.foodfinder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.domain.model.MenuDetail
import com.anggarad.dev.foodfinder.databinding.FragmentMenuDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuDetailFragment : BottomSheetDialogFragment() {
    companion object {
        const val MENU_DATA = "menu_data"
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    private lateinit var binding: FragmentMenuDetailBinding
    private var menuDetail: MenuDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuDetail = arguments?.getParcelable(MENU_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachData(menuDetail)
    }

    private fun attachData(menuDetail: MenuDetail?) {
        menuDetail?.let {
            with(binding) {
                menuTitleDetail.text = it.menuName
                menuPriceDetail.text = it.menuPrice
                menuDescriptionDetail.text = it.description
                Glide.with(requireContext())
                    .load(SERVER_URL + "uploads/${it.menuImg}")
                    .placeholder(R.drawable.ic_image)
                    .into(binding.ivMenuDetail)
                if (it.isRecommended == 0 || it.isRecommended == null) {
                    ivRecommendedIcon.visibility = View.GONE
                } else {
                    ivRecommendedIcon.visibility = View.VISIBLE
                }
            }

        }
    }


}