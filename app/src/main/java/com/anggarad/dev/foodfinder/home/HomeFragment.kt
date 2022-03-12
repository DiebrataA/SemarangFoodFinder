package com.anggarad.dev.foodfinder.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.ui.CategoriesAdapter
import com.anggarad.dev.foodfinder.core.ui.RestoAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentHomeBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import com.anggarad.dev.foodfinder.restolist.RestoByCategoryActivity
import com.anggarad.dev.foodfinder.search.SearchActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoriesAdapter: CategoriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val restoAdapter = RestoAdapter()
            restoAdapter.onItemClick = { selectedItem ->
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.RESTO_ID, selectedItem.restoId)
                startActivity(intent)
            }

            categoriesAdapter = CategoriesAdapter()
            categoriesAdapter.onItemClick = { categoryData ->
                val intent = Intent(activity, RestoByCategoryActivity::class.java)
                intent.putExtra(RestoByCategoryActivity.ARG_PARAM1, categoryData)
                startActivity(intent)
            }

            binding.searchView.setOnClickListener {
                val intentSearch = Intent(activity, SearchActivity::class.java)
                startActivity(intentSearch)
            }

            getResto(restoAdapter)

            getCategories(categoriesAdapter)

            with(binding.rvRestos) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = restoAdapter
            }

            with(binding.rvCategories) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = categoriesAdapter
            }

        }
    }

    private fun getCategories(categoriesAdapter: CategoriesAdapter) {
        homeViewModel.getCategories.observe(viewLifecycleOwner, { categories ->
            if (categories != null) {
                when (categories) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        categoriesAdapter.setCategoriesList(categories.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getResto(restoAdapter: RestoAdapter) {
        homeViewModel.getRestolist.observe(viewLifecycleOwner, { restoList ->
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
                }
            }
        })
    }


}