package com.anggarad.dev.foodfinder.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anggarad.dev.foodfinder.R

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }
}