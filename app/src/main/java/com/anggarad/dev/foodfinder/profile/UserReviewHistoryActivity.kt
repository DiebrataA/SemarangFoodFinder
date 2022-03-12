package com.anggarad.dev.foodfinder.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.ui.UserReviewAdapter
import com.anggarad.dev.foodfinder.databinding.ActivityUserReviewHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserReviewHistoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "user_id"
    }

    private lateinit var binding: ActivityUserReviewHistoryBinding
    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var userReviewAdapter: UserReviewAdapter
    private var userId: Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserReviewHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra(EXTRA_ID, 0)
        userReviewAdapter = UserReviewAdapter()

        userId?.let { getUserReviews(it) }

        with(binding.rvReviewUser) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userReviewAdapter
//            addItemDecoration(
//                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//            )
        }


    }

    private fun getUserReviews(userId: Int) {
        profileViewModel.getUserReviews(userId).observe(this, { reviewList ->

            when (reviewList) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    if (reviewList.data.isNullOrEmpty()) {
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
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

}