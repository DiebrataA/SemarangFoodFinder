package com.anggarad.dev.foodfinder.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
class HomeActivity : AppCompatActivity() {

//    companion object {
//        const val USER_ID = "user_id"
//    }

    private lateinit var binding: ActivityHomeBinding

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(findViewById(R.id.my_toolbar))
        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_home)

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment
//        ).build()
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//
//        requestLocationPermission()
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        requestLocationPermission()


//        val userId = intent.getIntExtra(USER_ID, 0)
//        val bundleData = Bundle()
//        bundleData.putInt(USER_ID, userId)

    }
//
//    private fun foregroundPermissionApproved(): Boolean {
//        return ActivityCompat.checkSelfPermission(this,
//            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }
//
//
//    private fun requestLocationPermission(){
//        if(foregroundPermissionApproved()){
//            locationPermissionGranted = true
//            bundleData = Bundle()
//            bundleData.putBoolean(IS_LOC_APPROVED, locationPermissionGranted)
//            navController.setGraph(R.navigation.home_navigation, bundleData)
//        } else {
//           return ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
//            )
//        }
//    }
//
//
//    private fun updateLocation() {
//
//      try {
//        if(locationPermissionGranted){
//            fusedLocationClient.lastLocation.addOnCompleteListener { task ->
//                if(task.isSuccessful){
//                    lastLocation = task.result
//
////                    val intent = intent.putExtra(USER_LOC, lastLocation)
//                    bundleData = Bundle()
//                    bundleData.putParcelable(USER_LOC, lastLocation)
//
////                    bundleData.putDouble(USER_LONG, lastLocation.longitude)
//                    navController.setGraph(R.navigation.home_navigation, bundleData)
////                    bundleData.putParcelable(USER_LOC, lastLocation)
////                    homeFragment.arguments = bundleData
//                    setLocation(lastLocation)
//                    Log.d("latitudeUser", lastLocation.latitude.toString())
//                } else {
//                    Log.d(TAG, "Current location is null. Using defaults.")
//                    Log.e(TAG, "Exception: %s", task.exception)
//                    binding.locationToolbar.text = Geocoder(this, Locale.getDefault()).getFromLocation(defaultLatitude,defaultLongitude,2)[0].adminArea
//                }
//            }
//        }
//      }catch (e: SecurityException){
//          Log.e("Exception: %s", e.message, e)
//      }
//
//        }
//
//    private fun setLocation(location: Location) {
//
//        var geocoder = Geocoder(this, Locale.getDefault())
//        var addresses = geocoder.getFromLocation(location.latitude, location.longitude, 2)
////        HomeFragment.newInstance(location.latitude, location.longitude)
//        address = addresses[0]
//        binding.locationToolbar.text = "${address.subLocality}, ${address.subAdminArea}"
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        locationPermissionGranted = false
//        when(requestCode){
//            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED
//                ) {
//                    locationPermissionGranted = true
//                }
//            }
//        }
//        updateLocation()
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        //        private val TAG = HomeActivity::class.java.simpleName
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 22
        const val IS_LOC_APPROVED = "isLocApproved"
//        const val USER_LONG = "userLong"
//        const val USER_LOC = "userLoc"
    }
}