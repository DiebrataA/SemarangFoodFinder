package com.anggarad.dev.foodfinder.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.databinding.ActivityDetailsBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailResto = intent.getParcelableExtra<RestoDetail>(EXTRA_DATA)

        val bundleData = Bundle()
        bundleData.putParcelable(EXTRA_DATA, detailResto)



        if (savedInstanceState == null) {
            val tabLayout = binding.tabDetail
            val viewPager = binding.detailReviewPager

            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, bundleData)
            viewPager.adapter = viewPagerAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Details"
                    }
                    1 -> {
                        tab.text = "Reviews"
                    }
                }
            }.attach()
        }



        showDetail(detailResto)
    }

    private fun showDetail(detailResto: RestoDetail?) {
        detailResto?.let {
            with(binding) {
                tvRestoTitle.text = detailResto.name
                tvDetailLocation.text = detailResto.location
                Glide.with(this@DetailsActivity)
                    .load("http://192.168.1.4:4000/uploads/${detailResto.imgCover}")
                    .into(ivImgCover)
                tvItemRatingDetail.text = detailResto.ratingAvg.toString()
                tvDetailCategory.text = detailResto.categories.toString()
            }
        }
    }
}