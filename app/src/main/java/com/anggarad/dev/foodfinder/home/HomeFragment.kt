package com.anggarad.dev.foodfinder.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.ui.CafeAdapter
import com.anggarad.dev.foodfinder.core.ui.RestoAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentHomeBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding


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
            val cafeAdapter = CafeAdapter()
            restoAdapter.onItemClick = { selectedItem ->
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.EXTRA_DATA, selectedItem)
                startActivity(intent)
            }


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

            homeViewModel.getCafelist.observe(viewLifecycleOwner, { cafeList ->
                if (cafeList != null) {
                    when (cafeList) {
//                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            cafeAdapter.setCafeList(cafeList.data)
                        }
                        is Resource.Error -> {
//                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                        }
                    }
                }
            })


            with(binding.rvRestos) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = restoAdapter
            }

            with(binding.rvCafe) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = cafeAdapter
            }

        }
    }

}