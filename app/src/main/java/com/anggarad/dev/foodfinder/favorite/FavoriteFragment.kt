package com.anggarad.dev.foodfinder.favorite

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.anggarad.dev.foodfinder.core.ui.FavoriteAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentFavoriteBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    companion object {
        const val EXTRA_LOC = "extra_loc"
    }

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: FragmentFavoriteBinding
    private var location: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favoriteAdapter = FavoriteAdapter()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        arguments?.let {
//            location = it.getParcelable(EXTRA_LOC)
//        }

//        val supportActionBar = Action
//        supportActionBar?.title = "Favorite"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.favoriteResto.observe(viewLifecycleOwner, { favList ->
            favoriteAdapter.setFavoriteList(favList)
            binding.viewEmpty.root.visibility =
                if (favList.isNotEmpty()) View.GONE else View.VISIBLE
        })
        updateLocation()
        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun updateLocation() {
        try {
            fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    location = task.result

                    if (task.result != null) {
                        favoriteAdapter.onItemClick = { selectedItem ->
                            val intent = Intent(activity, DetailsActivity::class.java)
                            intent.putExtra(DetailsActivity.RESTO_ID, selectedItem.restoId)
                            intent.putExtra(DetailsActivity.EXTRA_DATA, location)
                            startActivity(intent)
                        }
                    } else {
                        Log.d(EXTRA_LOC, "Current location is null. Using defaults.")
                        binding.locationToolbar.text = "Check Your Location Service"
                    }
                } else {
                    Log.d(EXTRA_LOC, "Current location is null. Using defaults.")
                    Log.e(EXTRA_LOC, "Exception: %s", task.exception)
                }
            }

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }

    }

}