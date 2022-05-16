package com.anggarad.dev.foodfinder.reviews

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.ui.ReviewAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentReviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_DETAIL = "param1"

class ReviewFragment : Fragment() {

    companion object {
        fun newInstance(detailResto: RestoDetail): ReviewFragment {
            val frag = ReviewFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_DETAIL, detailResto)
            frag.arguments = bundle
            return frag
        }
    }

    private val reviewViewModel: ReviewViewModel by viewModel()
    private lateinit var binding: FragmentReviewBinding
    private var restoId: Int? = 0
    private var detailResto: RestoDetail? = null
    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewDetails: ReviewDetails? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        arguments?.let {
            detailResto = it.getParcelable(ARG_DETAIL)
            restoId = detailResto?.restoId
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addReview.setOnClickListener {
            val intent = Intent(activity, PostReviewActivity::class.java)
            intent.putExtra(PostReviewActivity.EXTRA_DATA, restoId)
            startActivity(intent)
        }

        if (activity != null) {

            setDataReview()
        }
    }

    private fun setDataReview() {
        binding.tvReviewscreenAvg.text = detailResto?.ratingAvg.toString()
        reviewAdapter = ReviewAdapter()
        with(binding.rvReview) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = reviewAdapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }

        reviewAdapter.onItemClick = { review ->
            val reviewImageFragment = review.reviewsId?.let { ReviewImageFragment.newInstance(it) }
            reviewImageFragment?.let { it1 ->
                parentFragmentManager.beginTransaction().replace(R.id.frg_container_review,
                    it1).commit()
            }
        }

        val id = detailResto?.restoId

        if (id != null) {
            reviewViewModel.getRestoReviews(id).observe(viewLifecycleOwner, { reviewList ->
                if (reviewList != null) {
                    when (reviewList) {
                        is Resource.Success -> {
                            if (reviewList.data?.isEmpty() == true) {
                                binding.viewNoReview.root.visibility = View.VISIBLE
                            } else {
                                reviewAdapter.setReviewList(reviewList.data)
                                binding.tvReviewSum.text = "(${reviewList.data?.size} Reviews)"
                            }
                        }
                        is Resource.Error -> {
                            binding.viewNoReview.root.visibility = View.VISIBLE
                        }
                    }
                } else {
                    binding.viewNoReview.root.visibility = View.VISIBLE
                }
            })
        }


    }

    override fun onResume() {
        super.onResume()
        setDataReview()
    }

}