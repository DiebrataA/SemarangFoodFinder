package com.anggarad.dev.foodfinder.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

//    companion object {
//        const val USER_ID = "user_id"
//    }

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_home)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment, R.id.favoriteFragment, R.id.profileFragment
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val userId = intent.getIntExtra(USER_ID, 0)
//        val bundleData = Bundle()
//        bundleData.putInt(USER_ID, userId)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}