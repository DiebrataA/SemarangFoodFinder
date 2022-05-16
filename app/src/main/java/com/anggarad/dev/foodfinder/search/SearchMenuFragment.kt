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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.ui.MenuSearchAdapter
import com.anggarad.dev.foodfinder.core.ui.SearchAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentSearchMenuBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_QUERY = "param1"
private const val ARG_LOCATION = "param2"


class SearchMenuFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private var param1: String? = null
    private var param2: Location? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var binding: FragmentSearchMenuBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var searchMenuAdapter: MenuSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_QUERY)
            param2 = it.getParcelable(ARG_LOCATION)

            param2?.let { loc ->
                latitude = loc.latitude
                longitude = loc.longitude
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        param1?.apply {
            searchFunc(this, latitude, longitude)
        }
        searchAdapter = SearchAdapter()
        searchAdapter.onItemClick = { searchItem ->
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.RESTO_ID, searchItem.restoId)
            intent.putExtra(DetailsActivity.EXTRA_DATA, param2)
            startActivity(intent)
        }

        with(binding.rvSearch) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }
    }


    private fun searchFunc(query: String, lat: Double, lng: Double) {
        lifecycleScope.launchWhenStarted {
            searchViewModel.searchMenu(query)
                .observe(viewLifecycleOwner, { searchRes ->
                    when (searchRes) {

                        is Resource.Success -> {
                            if (searchRes.data?.isEmpty() == true) {
                                binding.progressBar.visibility = View.GONE
                                binding.noResult.visibility = View.VISIBLE
                                binding.dishesSearchTitle.visibility = View.GONE
                                binding.noResult.text = "There’s no resto have $query"
                            } else {
                                binding.progressBar.visibility = View.GONE
                                searchAdapter.setSearchList(searchRes.data, lat, lng)
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

    private fun searchResto(query: String) {
        if (query != null) {
            val searchRestoFragment = param2?.let { SearchRestoFragment.newInstance(query, it) }

            searchRestoFragment?.let {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.search_fragment_container,
                        it).commit()
            }
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    transaction.commit()
        }
    }

    companion object {

        fun newInstance(param1: String, param2: Location): SearchMenuFragment {
            val frag = SearchMenuFragment()
            val bundle = Bundle()
            bundle.putString(ARG_QUERY, param1)
            bundle.putParcelable(ARG_LOCATION, param2)
            frag.arguments = bundle
            return frag
        }
    }
}