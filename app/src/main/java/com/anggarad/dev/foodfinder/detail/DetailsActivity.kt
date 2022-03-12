package com.anggarad.dev.foodfinder.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.databinding.ActivityDetailsBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val RESTO_ID = "resto_id"
        const val SERVER_URL = BuildConfig.MY_SERVER_URL

    }

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailsBinding
    private var detailResto: RestoDetail? = null
    private lateinit var bundleData: Bundle
    private var menu: Menu? = null
    private var restoId: Int? = 0
    private var favState: Boolean? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()

//        setSupportActionBar(findViewById(R.id.my_toolbar))
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)

//        detailResto = intent.getParcelableExtra(EXTRA_DATA)

        restoId = intent.getIntExtra(RESTO_ID, 0)


//            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                private val listener = ViewTreeObserver.OnGlobalLayoutListener {
//                    val view = binding.detailReviewPager
//                    updatePagerHeightForChild(view)
//                }
//
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                    val view = binding.detailReviewPager
//                    // ... IMPORTANT: remove the global layout listener from other views
//                    binding..forEach {
//                        it.viewTreeObserver.removeOnGlobalLayoutListener(
//                            layoutListener
//                        )
//                    }
//                    view.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
//                }
//
//                private fun updatePagerHeightForChild(view: View) {
//                    view.post {
//                        val wMeasureSpec =
//                            MeasureSpec.makeMeasureSpec(view.width, MeasureSpec.EXACTLY)
//                        val hMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//                        view.measure(wMeasureSpec, hMeasureSpec)
//
//                        if (pager.layoutParams.height != view.measuredHeight) {
//                            // ParentViewGroup is, for example, LinearLayout
//                            // ... or whatever the parent of the ViewPager2 is
//                            pager.layoutParams =
//                                (pager.layoutParams as ParentViewGroup.LayoutParams)
//                                    .also { lp -> lp.height = view.measuredHeight }
//                        }
//                    }
//                }
//            })


        showDetailFromId(restoId)
//        showDetail(detailResto)
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
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.set_favorite)
        if (state == true) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_red)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black)
        }
    }


//    private fun showDetail(detailResto: RestoDetail?) {
//        detailResto?.let {
//            with(binding) {
//                tvRestoTitle.text = detailResto.name
//                tvDetailLocation.text = detailResto.location
//                Glide.with(this@DetailsActivity)
//                    .load(SERVER_URL + "uploads/${detailResto.imgCover}")
//                    .into(ivImgCover)
//                tvItemRatingDetail.text = detailResto.ratingAvg.toString()
//                tvDetailCategory.text =
//                    detailResto.categories.toString().replace("[", "").replace("]", "")
//            }
//        }
//    }

    private fun attachViewPager() {
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

    private fun showFavoriteState(restoId: Int?) {
        restoId?.let {
            detailViewModel.getDetailRestoTest(it).observe(this, {
                when (it) {
                    is Resource.Success -> detailViewModel.getDetailResto(restoId).observe(this, {
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
                    is Resource.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT)
                        .show()
                    is Resource.Success -> {
                        detailResto = it.data
                        bundleData = Bundle()
                        bundleData.putParcelable(EXTRA_DATA, detailResto)

                        favState = detailResto?.isFavorite
                        with(binding) {
                            tvRestoTitle.text = it.data?.name
                            tvDetailLocation.text = it.data?.location
                            Glide.with(this@DetailsActivity)
                                .load(SERVER_URL + "uploads/${it.data?.imgCover}")
                                .into(ivImgCover)
                            tvItemRatingDetail.text = it.data?.ratingAvg.toString()
                            tvDetailCategory.text =
                                it.data?.categories.toString().replace("[", "").replace("]", "")
                        }

                        attachViewPager()
                    }
                    is Resource.Error -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val collapsingToolbarLayout =
            findViewById<View>(R.id.collapsing_app_bar_detail) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = detailResto?.name

        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.text_secondary)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.white)
        )
//        detailResto?.let {
//            binding.categoryTitlePage.text = it.categoryName
//            Glide.with(this)
//                .load(RestoByCategoryActivity.SERVER_URL + "uploads/${it.categoryImg}")
//                .into(binding.ivCategoryLogo)
//        }

    }


}