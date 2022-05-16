package com.anggarad.dev.foodfinder.detail

import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.ui.MenuAdapter
import com.anggarad.dev.foodfinder.databinding.ActivityDetailsBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_DETAIL = "extra_detail"
        const val RESTO_ID = "resto_id"
        const val SERVER_URL = BuildConfig.MY_SERVER_URL

    }

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailsBinding
    private var detailResto: RestoDetail? = null
    private lateinit var bundleData: Bundle
    private var location: Location? = null
    private var menu: Menu? = null
    private var restoId: Int? = 0
    private var favState: Boolean? = false
    private lateinit var menuAdapter: MenuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()
        bundleData = Bundle()

        location = intent.getParcelableExtra(EXTRA_DATA)
        restoId = intent.getIntExtra(RESTO_ID, 0)

        showDetailFromId(restoId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu

        showFavoriteState(restoId)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.set_favorite) {
            favState = !favState!!
            detailResto?.let {
                detailViewModel.setFavoriteRestos(it, favState!!)
            }

        }

        return super.onOptionsItemSelected(item)
    }


    private fun setFavoriteState(state: Boolean?) {
//        if (state == true) {
//            binding.imgBtnFavDetail.setImageResource(R.drawable.fav_button_checked)
//        } else {
//            binding.imgBtnFavDetail.setImageResource(R.drawable.fav_button)
//        }

        if (menu == null) return
        val menuItem = menu?.findItem(R.id.set_favorite)
        if (state == true) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.fav_button_checked)
        } else {
            menuItem?.icon =
                ContextCompat.getDrawable(this, R.drawable.fav_button)
        }
    }

//    private fun attachViewPager() {
//        val tabLayout = binding.tabDetail
//        val viewPager = binding.detailReviewPager
//
//        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, bundleData)
//        viewPager.adapter = viewPagerAdapter
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            when (position) {
//                0 -> {
//                    tab.text = "Details"
//                }
//                1 -> {
//                    tab.text = "Reviews"
//                }
//            }
//        }.attach()
//    }

    private fun showFavoriteState(restoId: Int?) {
        restoId?.let {
            detailViewModel.getDetailRestoTest(it).observe(this, { fav ->
                when (fav) {
                    is Resource.Success -> detailViewModel.getDetailResto(it).observe(this, {
                        setFavoriteState(favState)
                    })
                }
            })
        }
    }

    private fun showDetailFromId(restoId: Int?) {

        restoId?.let {
            detailViewModel.getDetailRestoTest(restoId).observe(this, {
                when (it) {
                    is Resource.Success -> {
                        detailResto = it.data
                        bundleData.putParcelable(EXTRA_DETAIL, detailResto)
                        favState = detailResto?.isFavorite
                        Glide.with(this@DetailsActivity)
                            .load(SERVER_URL + "uploads/${it.data?.imgCover}")
                            .into(binding.ivImgCover)

                        val detailFragment =
                            location?.let { it1 ->
                                detailResto?.let { it2 ->
                                    DetailsRestoFragment.newInstance(it2,
                                        it1)
                                }
                            }
                        detailFragment?.let { it1 ->
                            supportFragmentManager.beginTransaction()
                                .setTransition(TRANSIT_FRAGMENT_FADE).replace(R.id.frg,
                                it1).commit()
                        }
//
//                        attachViewPager()
                    }
                    is Resource.Error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.my_toolbar))
//
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_button)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }




}