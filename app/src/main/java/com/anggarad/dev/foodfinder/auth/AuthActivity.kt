package com.anggarad.dev.foodfinder.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.DataStoreManager

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    }
}