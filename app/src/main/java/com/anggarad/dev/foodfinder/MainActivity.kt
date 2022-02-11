package com.anggarad.dev.foodfinder

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.anggarad.dev.foodfinder.auth.AuthActivity
import com.anggarad.dev.foodfinder.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            mainViewModel.checkToken.observe(this) { token ->
                val authIntent = Intent(this, AuthActivity::class.java)
                val homeIntent = Intent(this, HomeActivity::class.java)
                if (token == "") {
                    startActivity(authIntent)
                } else {
                    startActivity(homeIntent)
                }
            }
            finish()
        }, 3000)

//
//        mainViewModel.checkToken.observe(this) { token ->
//            val authIntent = Intent(this, AuthActivity::class.java)
//            val homeIntent = Intent(this, HomeActivity::class.java)
//            if (token == "") {
//                startActivity(authIntent)
//            } else {
//                startActivity(homeIntent)
//            }
//        }
    }
}
