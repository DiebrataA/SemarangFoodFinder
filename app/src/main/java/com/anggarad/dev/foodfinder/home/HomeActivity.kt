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


//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment, HomeFragment())
//                .commit()
//            supportActionBar?.title = "Food Finder"
//        }
//
//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            var fragment: Fragment? = null
//            var title = "Food Finder"
//            when (item.itemId) {
//                R.id.nav_home -> {
//                    fragment = HomeFragment()
//                    title = "Home"
//                }
//
//                R.id.nav_favorite -> {
//                    startActivity(
//                        Intent(
//                            this,
//                            Class.forName("com.anggarad.dev.favorite.FavoriteActivity")
//                        )
//                    )
//                }
//
//                R.id.nav_profile -> {
//                    fragment = ProfileFragment()
//                    title = "Profile"
//                }
//            }
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


//
//    private fun setCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.nav_host_fragment, fragment)
//                .commit()
//        }


//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        var fragment: Fragment? = null
//        var title = "Food Finder"
//        when (item.itemId) {
//            R.id.nav_home -> {
//                fragment = HomeFragment()
//                title = "Home"
//            }
//
//            R.id.nav_favorite -> {
//                startActivity(
//                    Intent(
//                        this,
//                        Class.forName("com.anggarad.dev.favorite.FavoriteActivity")
//                    )
//                )
//            }
//
//            R.id.nav_profile -> {
//                fragment = ProfileFragment()
//                title = "Profile"
//            }
//        }
//        if (fragment != null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment, fragment)
//                .commit()
//        }
//        supportActionBar?.title = title
//
//        binding.drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }
}