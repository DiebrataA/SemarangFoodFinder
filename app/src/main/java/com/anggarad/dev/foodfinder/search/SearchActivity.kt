package com.anggarad.dev.foodfinder.search

import android.app.SearchManager
import android.content.Context
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.databinding.ActivitySearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchBinding
    private var location: Location? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar_search))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        location = intent.getParcelableExtra(EXTRA_LOC)

        location?.let {
            latitude = it.latitude
            longitude = it.longitude
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
                    val searchResultFragment =
                        location?.let { SearchMenuFragment.newInstance(query, it) }
                    val searchRestoFragment =
                        location?.let { SearchRestoFragment.newInstance(query, it) }

                    searchResultFragment?.let {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.searchMenu_fragment_container,
                                it).commit()
                    }
                    searchRestoFragment?.let {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.search_fragment_container, it).commit()
                    }
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    transaction.commit()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    companion object {
        const val EXTRA_LOC = "extra_loc"
        const val EXTRA_QUERY = "extra_query"
    }
}