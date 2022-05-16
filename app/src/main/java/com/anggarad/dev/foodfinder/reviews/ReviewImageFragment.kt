package com.anggarad.dev.foodfinder.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.databinding.FragmentReviewImageBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_REVIEW = "review_detail"

class ReviewImageFragment : Fragment() {
    private var param1: Int? = null
    private lateinit var binding: FragmentReviewImageBinding
    private val reviewViewModel: ReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_REVIEW)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        param1?.let { attachData(it) }
    }

    private fun attachData(reviewId: Int) {
        reviewViewModel.getReviewById(reviewId).observe(viewLifecycleOwner, { review ->
            when (review) {
                is Resource.Loading -> Toast.makeText(requireContext(),
                    "Loading",
                    Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    with(binding) {
                        Glide.with(requireContext())
                            .load("${review.data?.imgReviewPath}")
                            .into(imageView)
                        ratingBar3.rating = review.data?.rating?.toFloat() ?: 0f
                        tvUserNameReviewImage.text = review.data?.name
                        tvReviewImageComments.text = review.data?.comments
                    }
                }
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            ReviewImageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_REVIEW, param1)
                }
            }

        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }
}