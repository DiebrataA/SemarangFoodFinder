package com.anggarad.dev.foodfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.auth.AuthActivity
import com.anggarad.dev.foodfinder.auth.AuthViewModel
import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.utils.startNewActivity
import com.anggarad.dev.foodfinder.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainViewModel.checkToken.observe(this) { token ->
            Log.d("token:", token.toString())
            val authIntent = Intent(this, AuthActivity::class.java)
            val homeIntent = Intent(this, HomeActivity::class.java)
            if (token == "") {
                startActivity(authIntent)
            } else {
                startActivity(homeIntent)
            }
        }
    }
}
