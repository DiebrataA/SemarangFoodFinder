package com.anggarad.dev.foodfinder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.ui.ReviewAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentReviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReviewFragment : Fragment() {

    private val reviewViewModel: ReviewViewModel by viewModel()
    private lateinit var binding: FragmentReviewBinding
    private var restoId: Int? = 0
    private var detailResto: RestoDetail? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailResto = arguments?.getParcelable<RestoDetail>(DetailsActivity.EXTRA_DATA)
        restoId = detailResto?.restoId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reviewAdapter = ReviewAdapter()

        with(binding.rvReview) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = reviewAdapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }



        if (activity != null) {
            detailResto = arguments?.getParcelable(DetailsActivity.EXTRA_DATA)

            val id = detailResto?.restoId

            if (id != null) {
                reviewViewModel.getRestoReviews(id).observe(viewLifecycleOwner, { reviewList ->
                    if (reviewList != null) {
                        when (reviewList) {
                            is Resource.Success -> {
                                reviewAdapter.setReviewList(reviewList.data)
                                Toast.makeText(requireContext(), "Berhasil", Toast.LENGTH_SHORT)
                                    .show()
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
    }


}