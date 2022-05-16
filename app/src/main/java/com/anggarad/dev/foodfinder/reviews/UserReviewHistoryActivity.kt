package com.anggarad.dev.foodfinder.reviews

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.ui.UserReviewAdapter
import com.anggarad.dev.foodfinder.databinding.ActivityUserReviewHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserReviewHistoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "user_id"
        const val EDIT_REVIEW_TAG = "edit_review_tag"
    }

    private lateinit var binding: ActivityUserReviewHistoryBinding
    private val profileViewModel: ReviewViewModel by viewModel()
    private lateinit var userReviewAdapter: UserReviewAdapter
    private var userId: Int? = 0
    private lateinit var bundle: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserReviewHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = Bundle()

        userId = intent.getIntExtra(EXTRA_ID, 0)
        userReviewAdapter = UserReviewAdapter()
        supportActionBar?.show()
        supportActionBar?.title = "User Review History"

        userId?.let { getUserReviews(it) }

        userReviewAdapter.onItemClick = { selectedItem ->
            bundle.putParcelable(EditReviewFragment.REVIEW_DATA, selectedItem)
            val editReviewFragment = EditReviewFragment()
            editReviewFragment.arguments = bundle
            editReviewFragment.show(supportFragmentManager, EDIT_REVIEW_TAG)
        }

        with(binding.rvReviewUser) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userReviewAdapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
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
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        userReviewAdapter.setReviewList(reviewList.data)
                        Toast.makeText(this, "Berhasil $userId", Toast.LENGTH_SHORT)
                            .show()
                    }

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