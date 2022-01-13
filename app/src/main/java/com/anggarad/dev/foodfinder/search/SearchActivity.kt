package com.anggarad.dev.foodfinder.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.ui.SearchAdapter
import com.anggarad.dev.foodfinder.databinding.ActivitySearchBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar_search))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val searchAdapter = SearchAdapter()
        searchAdapter.onItemClick = { searchItem ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.EXTRA_DATA, searchItem)
            startActivity(intent)
        }


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchRestoWidget

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            requestFocus()
            queryHint = resources.getString(R.string.search_hint)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    lifecycleScope.launchWhenStarted {
                        searchViewModel.searchResto(query)
                            .observe(this@SearchActivity, { searchRes ->

                                when (searchRes) {
                                    is ApiResponse.Success -> {
                                        searchAdapter.setSearchList(searchRes.data)
                                        Toast.makeText(
                                            this@SearchActivity,
                                            "Search Result available",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    is ApiResponse.Error -> {
                                        Toast.makeText(
                                            this@SearchActivity,
                                            "Error Search",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    is ApiResponse.Empty -> {
                                        Toast.makeText(
                                            this@SearchActivity,
                                            "No Data",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                            })
                    }
                }


                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        with(binding.rvSearch) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }
    }
}