package com.anggarad.dev.foodfinder.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserReviewDetails
import com.anggarad.dev.foodfinder.databinding.FragmentEditReviewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val USER_ID = "param1"

class EditReviewFragment : BottomSheetDialogFragment() {
    private var userId: Int? = null
    private val reviewViewModel: ReviewViewModel by viewModel()
    private lateinit var binding: FragmentEditReviewBinding
    private lateinit var etComments: EditText
    private var ratingResult: Float = 0.0f
    private var reviewDetail: UserReviewDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewDetail = arguments?.getParcelable(REVIEW_DATA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEditReviewBinding.inflate(inflater, container, false)

        etComments = binding.editCommentsField
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        attachData(reviewDetail)

        binding.btnUpdate.setOnClickListener {
            updateReview(0, UPDATE_SUCCESS_MSG)
        }
        binding.btnDeleteReview.setOnClickListener {
            showAlert(it)
        }

        binding.ratingBar2.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingResult = rating
//            ratingResult = if(fromUser){
//                rating
//            } else {
//                reviewDetail?.rating ?: 0f
//            }

        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun attachData(reviewDetails: UserReviewDetails?) {
        reviewDetails?.let {
            with(binding) {
                ratingBar2.stepSize = 1f
                ratingBar2.rating = it.rating
                ratingResult = ratingBar2.rating
                etComments.setText(it.comments)
            }
        }
    }

    private fun updateReview(isDeleted: Int, msg: String) {

        val comments = etComments.text.toString().trim()
        val id = reviewDetail?.reviewsId

        reviewViewModel.updateReview(id, comments, ratingResult, isDeleted)
            .observe(viewLifecycleOwner, { editReview ->
                when (editReview) {
                    is Resource.Loading -> binding.progressBar2.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(requireContext(),
                            msg, Toast.LENGTH_SHORT).show()

                    }
                    is Resource.Error -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(requireContext(),
                            "Error Updating Review", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

    private fun deleteReview() {
        updateReview(1, DELETE_SUCCESS_MSG)
    }

    fun showAlert(view: View) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete this Review?")
            .setMessage("Deleted Review will not be showing in your history and Resto Review.")
            .setNegativeButton("No") { dialog, which ->
                Toast.makeText(requireContext(), "Delete Canceled", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Yes") { dialog, which ->
                deleteReview()
            }.show()
    }

    companion object {
//        @JvmStatic
//        fun newInstance(userId: Int) =
//            UserReviewListFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(USER_ID, userId)
//                }
//            }

        const val UPDATE_SUCCESS_MSG = "Review Udpated Successsfully"
        const val DELETE_SUCCESS_MSG = "Delete Review Success"
        const val REVIEW_DATA = "review_data"
    }
}