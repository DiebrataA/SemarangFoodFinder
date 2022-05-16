package com.anggarad.dev.foodfinder.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anggarad.dev.foodfinder.auth.AuthActivity
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.databinding.FragmentProfileBinding
import com.anggarad.dev.foodfinder.reviews.UserReviewHistoryActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding
    private var userId: String = ""
    private lateinit var fAuth: FirebaseAuth

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fAuth = Firebase.auth
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

        fAuth.currentUser?.uid.let {
            if (it != null) {
                userId = it
                fetchUserData(it)
            }
        }

        binding.btnLogout.setOnClickListener {
            fAuth.signOut()
            val intentAuth = Intent(activity, AuthActivity::class.java)
            startActivity(intentAuth)
            requireActivity().finish()
        }

        binding.btnEditProfile.setOnClickListener {
            val intentEdit = Intent(activity, EditProfileActivity::class.java)
            intentEdit.putExtra(EditProfileActivity.USER_ID, userId)
            startActivity(intentEdit)
        }

//        profileViewModel.userId.observe(viewLifecycleOwner, { id ->
//            userId = id
//            Log.d("User ID:", id.toString())
//            getUser(userId)
//            onClickUserReviews(userId)
//        })


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

    private fun fetchUserData(userId: String) {
        profileViewModel.fetchUserDetail(userId).observe(viewLifecycleOwner, { user ->
            when (user) {
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.viewError.root.visibility = View.VISIBLE
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvProfileName.text = user.data?.fullName
                    binding.tvProfileEmail.text = user.data?.email
                    binding.tvPhone.text = user.data?.phoneNum
                    binding.tvUserAddress.text = user.data?.address
                    Glide.with(requireContext())
                        .load(user.data?.imgProfile)
                        .into(binding.ivAvatarProfile)

                    user.data?.userId?.let { onClickUserReviews(it) }
                }
            }
        })

    }

    private fun getUser(userId: Int) {

        profileViewModel.getUserDetail(userId).observe(viewLifecycleOwner, { user ->

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
                    binding.viewError.root.visibility = View.VISIBLE
                }

            }


        })


    }

    private fun onClickUserReviews(userId: Int) {
        binding.btnReviewHistory2.setOnClickListener {
            val intentUserReview = Intent(activity, UserReviewHistoryActivity::class.java)
            intentUserReview.putExtra(UserReviewHistoryActivity.EXTRA_ID, userId)
            startActivity(intentUserReview)
        }
    }

    override fun onResume() {
        super.onResume()
//                val userId = arguments?.getInt(HomeActivity.USER_ID)
        fetchUserData(userId)
    }


}

