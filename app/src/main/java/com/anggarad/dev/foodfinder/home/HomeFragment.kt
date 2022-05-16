package com.anggarad.dev.foodfinder.home

import android.Manifest
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.ui.CafeAdapter
import com.anggarad.dev.foodfinder.core.ui.CategoriesAdapter
import com.anggarad.dev.foodfinder.core.ui.RestoAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentHomeBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import com.anggarad.dev.foodfinder.restolist.RestoByCategoryActivity
import com.anggarad.dev.foodfinder.search.SearchActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var restoAdapter: RestoAdapter
    private lateinit var popularAdapter: CafeAdapter
    private lateinit var lastLocation: Location
    private lateinit var address: Address
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var geocoder: Geocoder
    private lateinit var bundle: Bundle
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val defaultLatitude: Double = -2.5516865435420697
    private val defaultLongitude: Double = 107.7137402610472

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoAdapter = RestoAdapter()
        categoriesAdapter = CategoriesAdapter()
        popularAdapter = CafeAdapter()
        geocoder = Geocoder(context, Locale.getDefault())
        bundle = Bundle()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        swipeToRefresh = binding.swipeToRefresh
//        requireActivity().actionBar?.hide()
        restoAdapter.onItemClick = { selectedItem ->
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.RESTO_ID, selectedItem.restoId)
            intent.putExtra(DetailsActivity.EXTRA_DATA, lastLocation)
            startActivity(intent)
        }
        categoriesAdapter.onItemClick = { categoryData ->
            val intent = Intent(activity, RestoByCategoryActivity::class.java)
            intent.putExtra(RestoByCategoryActivity.ARG_PARAM1, categoryData)
            intent.putExtra(RestoByCategoryActivity.ARG_LOC, lastLocation)
            startActivity(intent)
        }
        popularAdapter.onItemClick = { popularItem ->
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.RESTO_ID, popularItem.restoId)
            intent.putExtra(DetailsActivity.EXTRA_DATA, lastLocation)
            startActivity(intent)
        }
        binding.searchView.setOnClickListener {
            val intentSearch = Intent(activity, SearchActivity::class.java)
            intentSearch.putExtra(SearchActivity.EXTRA_LOC, lastLocation)
            startActivity(intentSearch)
        }
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

        with(binding.rvPopular) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = popularAdapter
        }
        attachView()



        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permission.",
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun getCategories(categoriesAdapter: CategoriesAdapter) {
        homeViewModel.getCategories.observe(viewLifecycleOwner, { categories ->
            if (categories != null) {
                when (categories) {
//                    is Resource.Loading -> binding.progressBar.root.showShimmer(true)
                    is Resource.Success -> {
//                        binding.progressBar.root.visibility = View.GONE
                        categoriesAdapter.setCategoriesList(categories.data)
                    }
//                    is Resource.Error -> {
////                        binding.progressBar.root.visibility = View.GONE
//                        binding.viewError.root.visibility = View.VISIBLE
//                    }
                }
            }
        })
    }

    private fun getPopularResto(cafeAdapter: CafeAdapter, refresh: Boolean) {
        homeViewModel.getRestolist(refresh).observe(viewLifecycleOwner, { popularList ->
            if (popularList != null) {
                when (popularList) {
                    is Resource.Success -> {
                        cafeAdapter.setCafeList(popularList.data,
                            lastLocation.latitude,
                            lastLocation.longitude)
                        swipeToRefresh.isRefreshing = false
                    }
                }
            }
        })
    }

    private fun getResto(
        restoAdapter: RestoAdapter,
        categoriesAdapter: CategoriesAdapter,
        refresh: Boolean,
    ) {
        homeViewModel.getRestolist(refresh).observe(viewLifecycleOwner, { restoList ->
            if (restoList != null) {
                when (restoList) {
                    is Resource.Loading -> binding.progressBar.root.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.root.visibility = View.GONE
                        restoAdapter.setRestoList(restoList.data,
                            lastLocation.latitude, lastLocation.longitude)
                        getCategories(categoriesAdapter)
                        swipeToRefresh.isRefreshing = false
                    }
                    is Resource.Error -> {
                        binding.progressBar.root.visibility = View.GONE
                        Toast.makeText(context, "Something is wrong", Toast.LENGTH_SHORT).show()
                        swipeToRefresh.isRefreshing = false
                    }
                }
            }
        })
    }

//    private fun foregroundPermissionApproved(): Boolean {
//        return checkSelfPermission(requireContext(),
//            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
//            requireContext(),
//            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }


//    private fun requestLocationPermission(){
//        if(foregroundPermissionApproved()){
//            locationPermissionGranted = true
//            updateLocation()
//        } else {
//            return requestPermissions(
//                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
//            )
//        }
//    }

    private fun attachView() {

        if (hasLocationPermission()) {
            updateLocation()
            swipeToRefresh.setOnRefreshListener {
                getResto(restoAdapter, categoriesAdapter, true)
                getPopularResto(popularAdapter, true)
            }
        } else {
            return requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }


    private fun updateLocation() {
        try {
            fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    lastLocation = task.result
                    getResto(restoAdapter, categoriesAdapter, false)
                    getPopularResto(popularAdapter, false)

                    if (task.result != null) {
                        setLocation(lastLocation)

//                        bundle.putParcelable(FavoriteFragment.EXTRA_LOC, lastLocation)
//                        FavoriteFragment().arguments = bundle
//                        parentFragmentManager.beginTransaction().commit()
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        binding.locationToolbar.text = "Check Your Location Service"
                    }
                } else {
                    Log.d(TAG, "Current location is null. Using defaults.")
                    Log.e(TAG, "Exception: %s", task.exception)
                    binding.locationToolbar.text =
                        Geocoder(requireContext(), Locale.getDefault()).getFromLocation(
                            defaultLatitude,
                            defaultLongitude,
                            2)[0].adminArea
                }
            }

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }

    }

    private fun setLocation(location: Location) {
        var addresses = geocoder.getFromLocation(location.latitude, location.longitude, 2)

        address = addresses[0]
        binding.locationToolbar.text = "${address.subLocality}, ${address.subAdminArea}"
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestLocationPermission()
        }
    }


    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            requireContext(),
            "Permission Granted!",
            Toast.LENGTH_SHORT
        ).show()
        attachView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    override fun onResume() {
        super.onResume()
        updateLocation()
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 22

    }
}