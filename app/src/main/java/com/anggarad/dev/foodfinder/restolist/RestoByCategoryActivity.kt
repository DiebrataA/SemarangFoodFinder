package com.anggarad.dev.foodfinder.restolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.CategoriesDetail
import com.anggarad.dev.foodfinder.core.ui.RestoByCategoryAdapter
import com.anggarad.dev.foodfinder.databinding.ActivityRestoByCategoryBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestoByCategoryActivity : AppCompatActivity() {
    private val restoByCategoriesViewModel: RestoByCategoryViewModel by viewModel()
    private var categoriesData: CategoriesDetail? = null
    private lateinit var restoAdapter: RestoByCategoryAdapter
    private lateinit var binding: ActivityRestoByCategoryBinding

    companion object {
        const val ARG_PARAM1 = "param1"
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestoByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoriesData = intent.getParcelableExtra(ARG_PARAM1)
        restoAdapter = RestoByCategoryAdapter()
        restoAdapter.onItemClick = { selectedItem ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.RESTO_ID, selectedItem.restoId)
            startActivity(intent)
        }

        setUpToolbar()
        categoriesData?.categoryId?.let { getRestoList(it) }

        with(binding.rvRestoListByCategory) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = restoAdapter
        }
    }


    private fun getRestoList(categoryId: Int) {


        restoByCategoriesViewModel.getRestoByCategory(categoryId).observe(this, { restoList ->
            if (restoList != null) {
                when (restoList) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        restoAdapter.setRestoList(restoList.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                    }
//                else -> {
//                    binding.progressBar.visibility = View.GONE
//                    binding.viewError.root.visibility = View.VISIBLE
//                }
                }
            }
        })


    }

    private fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.category_toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val collapsingToolbarLayout =
            findViewById<View>(R.id.collapsing_app_bar) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = categoriesData?.categoryName

        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.black_60)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.white)
        )
        categoriesData?.let {
            binding.categoryTitlePage.text = it.categoryName
            Glide.with(this)
                .load(SERVER_URL + "uploads/${it.categoryImg}")
                .into(binding.ivCategoryLogo)
        }

    }


}