package com.anggarad.dev.foodfinder.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.databinding.ActivityDetailsBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailsBinding
    private var detailResto: RestoDetail? = null
    private var menu: Menu? = null
    private var favState: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        detailResto = intent.getParcelableExtra(EXTRA_DATA)

        val bundleData = Bundle()
        bundleData.putParcelable(EXTRA_DATA, detailResto)

        favState = detailResto?.isFavorite

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu

        detailResto?.let {
            detailViewModel.getDetailResto(it.restoId).observe(this, { detail ->
                setFavoriteState(favState)
                Log.d("FavState: ", favState.toString())
            })
        }

//        val favIcon = menu?.findItem(R.id.set_favorite)
//
//        if(detailResto?.isFavorite == true){
//            favIcon?.setIcon(R.drawable.ic_favorite_red)
//        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


//        detailResto?.let {
//            detailViewModel.setFavoriteRestos(it, favStatus == true)
//            Toast.makeText(this, "fav!", Toast.LENGTH_SHORT).show()
//        }
        if (id == R.id.set_favorite) {
            favState = !favState!!
            detailResto?.let {
                detailViewModel.setFavoriteRestos(it, favState!!)
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
            }
//                favState = !favState!!
//                setFavoriteState(favState)

        }

        return super.onOptionsItemSelected(item);
    }

    private fun setFavoriteState(state: Boolean?) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.set_favorite)
        if (state == true) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_red)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black)
        }
    }


    private fun showDetail(detailResto: RestoDetail?) {
        detailResto?.let {
            with(binding) {
                tvRestoTitle.text = detailResto.name
                tvDetailLocation.text = detailResto.location
                Glide.with(this@DetailsActivity)
                    .load("http://192.168.1.3:4000/uploads/${detailResto.imgCover}")
                    .into(ivImgCover)
                tvItemRatingDetail.text = detailResto.ratingAvg.toString()
                tvDetailCategory.text =
                    detailResto.categories.toString().replace("[", "").replace("]", "")
            }
        }
    }


}