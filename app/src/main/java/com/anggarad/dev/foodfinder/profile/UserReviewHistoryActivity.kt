package com.anggarad.dev.foodfinder.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.ui.ReviewAdapter
import com.anggarad.dev.foodfinder.databinding.ActivityUserReviewHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserReviewHistoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "user_id"
    }

    private lateinit var binding: ActivityUserReviewHistoryBinding
    private val profileViewModel: ProfileViewModel by viewModel()
    private var userId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserReviewHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userReviewAdapter = ReviewAdapter()
        userId = intent.getIntExtra(EXTRA_ID, 0)

        with(binding.rvReview) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userReviewAdapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }

        profileViewModel.getUserReviews(userId).observe(this, { reviewList ->
            if (reviewList != null) {
                when (reviewList) {
//                    is Resource.Loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
//                    }
                    is Resource.Success -> {
                        if (reviewList.data?.isEmpty() == true) {
                            binding.viewNoReview.root.visibility = View.VISIBLE
                        }
                        binding.progressBar.visibility = View.GONE
                        userReviewAdapter.setReviewList(reviewList.data)
                        Toast.makeText(this, "Berhasil $userId", Toast.LENGTH_SHORT)
                            .show()


                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewNoReview.root.visibility = View.VISIBLE
                    }
                }
            } else {
                binding.viewNoReview.root.visibility = View.VISIBLE
            }
        })
    }

}