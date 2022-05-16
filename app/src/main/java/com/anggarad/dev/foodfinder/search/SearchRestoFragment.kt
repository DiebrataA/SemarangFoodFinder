package com.anggarad.dev.foodfinder.search

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.ui.SearchRestoAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentSearchRestoBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_QUERY_RESTO = "query_resto"
private const val ARG_LOCATION = "location_resto"

class SearchRestoFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private var query: String? = null
    private var location: Location? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var binding: FragmentSearchRestoBinding
    private lateinit var searchAdapter: SearchRestoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY_RESTO)
            location = it.getParcelable(ARG_LOCATION)

            location?.let { loc ->
                latitude = loc.latitude
                longitude = loc.longitude
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchRestoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        query?.let {
            searchResto(it, latitude, longitude)
        }

        searchAdapter = SearchRestoAdapter()
        searchAdapter.onItemClick = { searchItem ->
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.RESTO_ID, searchItem.restoId)
            intent.putExtra(DetailsActivity.EXTRA_DATA, location)
            startActivity(intent)
        }

        with(binding.rvSearchResto) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }

    private fun searchResto(query: String, lat: Double, lng: Double) {
        lifecycleScope.launchWhenStarted {
            searchViewModel.searchResto(query)
                .observe(viewLifecycleOwner, { searchRes ->
                    when (searchRes) {
                        is Resource.Success -> {
                            if (searchRes.data?.isEmpty() == true) {
                                binding.progressBar.visibility = View.GONE
//                                binding.noResult.root.visibility = View.VISIBLE
                            } else {
                                binding.progressBar.visibility = View.GONE
                                searchAdapter.setSearchList(searchRes.data, lat, lng)
                                Toast.makeText(
                                    requireContext(),
                                    "Search Result available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Error Search",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                    }

                })
        }
    }

    companion object {

        fun newInstance(param1: String, param2: Location): SearchRestoFragment {
            val frag = SearchRestoFragment()
            val bundle = Bundle()
            bundle.putString(ARG_QUERY_RESTO, param1)
            bundle.putParcelable(ARG_LOCATION, param2)
            frag.arguments = bundle
            return frag
        }
    }
}