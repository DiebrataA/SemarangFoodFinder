package com.anggarad.dev.foodfinder.reviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.databinding.ActivityReviewsBinding

class ReviewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewsBinding
    private var detailResto: RestoDetail? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        detailResto = intent.getParcelableExtra(EXTRA_DATA)
        startReviewFragment(detailResto)
    }

    private fun startReviewFragment(detailResto: RestoDetail?) {
        val reviewFragment = detailResto?.let { ReviewFragment.newInstance(it) }
        reviewFragment?.let {
            supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frg_container_review,
                    it).commit()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.my_toolbar_reviews))
        binding.toolbarReviewsTitle.text = getString(R.string.review_title)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}