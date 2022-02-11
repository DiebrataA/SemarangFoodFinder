package com.anggarad.dev.foodfinder.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding
    private var userId: Int = 0

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickUserReviews()

        profileViewModel.userId.observe(viewLifecycleOwner, { id ->
            userId = id
            getUser(userId)
        })

//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            profileViewModel.userResponse.collect { data ->
//                when (data) {
//                    is ApiResponse.Empty -> {
//                        binding.progressBar.visibility = View.VISIBLE
//                    }
//                    is ApiResponse.Success -> {
//
//                        binding.progressBar.visibility = View.GONE
//                        binding.tvProfileName.text = data.data.response.name
//                        binding.tvProfileEmail.text = data.data.response.email
//                        binding.tvPhone.text = data.data.response.phoneNum
//                        binding.tvUserAddress.text = data.data.response.address
//
//                        Glide.with(requireContext())
//                            .load("http://192.168.1.3:4000/uploads/${data.data.response.imgProfile}")
//                            .into(binding.ivAvatarProfile)
//
//                    }
//                    is ApiResponse.Error -> {
//                        binding.progressBar.visibility = View.GONE
//                        Toast.makeText(requireContext(), "Errr", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }

    }

    private fun getUser(userId: Int) {
        profileViewModel.getUserDetail(userId).observe(viewLifecycleOwner, { user ->
            if (user != null) {
                when (user) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvProfileName.text = user.data?.fullName
                        binding.tvProfileEmail.text = user.data?.email
                        binding.tvPhone.text = user.data?.phoneNum
                        binding.tvUserAddress.text = user.data?.address
                        Glide.with(requireContext())
                            .load(SERVER_URL + "uploads/${user.data?.imgProfile}")
                            .into(binding.ivAvatarProfile)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Errr", Toast.LENGTH_SHORT).show()
                    }

                }
            }

        })
    }

    private fun onClickUserReviews() {
        binding.btnReviewHistory2.setOnClickListener {
            val intentUserReview = Intent(activity, UserReviewHistoryActivity::class.java)
            intentUserReview.putExtra(UserReviewHistoryActivity.EXTRA_ID, userId)
            startActivity(intentUserReview)
        }
    }

}

