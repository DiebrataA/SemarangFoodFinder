package com.anggarad.dev.foodfinder

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.anggarad.dev.foodfinder.auth.AuthActivity
import com.anggarad.dev.foodfinder.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var handler: Handler
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fAuth = Firebase.auth

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
//            mainViewModel.checkToken.observe(this) { token ->
            val authIntent = Intent(this, AuthActivity::class.java)
            val homeIntent = Intent(this, HomeActivity::class.java)
//                if (token == "") {
//                    startActivity(authIntent)
//                } else {
//                    startActivity(homeIntent)
//                }
//            }
            val currentUser = fAuth.currentUser
            if (currentUser != null) {
                startActivity(homeIntent)
            } else {
                startActivity(authIntent)
            }
            finish()
        }, 2000)

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

//    override fun onStart() {
//        super.onStart()
//        val homeIntent = Intent(this, HomeActivity::class.java)
//        val authIntent = Intent(this, AuthActivity::class.java)
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            startActivity(homeIntent);
//        } else {
//            startActivity(authIntent)
//        }
//    }
}
