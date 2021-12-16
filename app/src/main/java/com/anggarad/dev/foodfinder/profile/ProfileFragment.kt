package com.anggarad.dev.foodfinder.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding
    private var userId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {


        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        profileViewModel.userId.observe(viewLifecycleOwner, { id ->
            userId = id
            profileViewModel.getUSerDetail(userId)
        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileViewModel.userResponse.collect { data ->
                when (data) {
                    is ApiResponse.Empty -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvProfileName.text = data.data.response.name
                        binding.tvProfileEmail.text = data.data.response.email
                        binding.tvPhone.text = data.data.response.phoneNum
                        binding.tvUserAddress.text = data.data.response.address

                        Glide.with(requireContext())
                            .load("http://192.168.1.4:4000/uploads/${data.data.response.imgProfile}")
                            .into(binding.ivAvatarProfile)
                    }
                    is ApiResponse.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Errr", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

//        if (activity != null) {
//            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//                profileViewModel.userId().observe(viewLifecycleOwner, { id ->
//
//                    if (id!== null){
//
//                    profileViewModel.getUSerDetail(id)
//
//
////                        profileViewModel.getUserDetail(id).observe(viewLifecycleOwner, { userDetail ->
////                            if (userDetail != null) {
////                                when (userDetail) {
////                                    is Resource.Loading -> { binding.progressBar.visibility = View.VISIBLE}
////                                    is Resource.Success -> {
////                                        binding.progressBar.visibility = View.GONE
////                                        userDetail.data?.let {
////
////                                            binding.tvProfileName.text = it.fullName
////                                            binding.tvProfileEmail.text = it.email
////                                            binding.tvPhone.text = it.phoneNum
////                                            binding.tvUserAddress.text = it.address
////
////                                            Glide.with(requireContext())
////                                                .load("http://192.168.1.4:4000/uploads/${it.imgProfile}")
////                                                .into(binding.ivAvatarProfile)
////
////                                        }
////                                    }
////                                    is Resource.Error -> {
////                                        binding.progressBar.visibility = View.GONE
////                                        binding.viewError.root.visibility = View.VISIBLE
////                                        Toast.makeText(requireContext(), "Eror Bung", Toast.LENGTH_SHORT)
////                                            .show()
////                                    }
////
////                                }
////
////                            }
////                        })
//                    } else {
//                        Toast.makeText(requireContext(), "No id", Toast.LENGTH_SHORT).show()
//                    }
//
//
//                })
//            }
//
//
//
//
//
//
//
//        } else {
//            binding.viewError.root.visibility = View.VISIBLE
//        }


    }

//    private fun getDetails(userId: Int){
//        return profileViewModel.getUserDetail(userId).observe(viewLifecycleOwner, {userDetail ->
//            if(userDetail != null) {
//                when (userDetail) {
//                    is Resource.Loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
//                    }
//                    is Resource.Success -> {
//                        binding.progressBar.visibility = View.GONE
//
//
//                        binding.tvProfileName.text = userDetail.data?.fullName ?: ""
//                        binding.tvProfileEmail.text = userDetail.data?.email ?: "no email"
//                        binding.tvPhone.text = userDetail.data?.phoneNum ?: "0"
//                        binding.tvUserAddress.text = userDetail.data?.address ?: ""
//
//                        Glide.with(this)
//                            .load("http://192.168.1.5:4000/uploads/${userDetail.data?.imgProfile}")
//                            .into(binding.ivAvatarProfile)
//
//
//                    }
//                    is Resource.Error -> {
//                        binding.progressBar.visibility = View.GONE
//                        Toast.makeText(requireContext(), "Eror Bung", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//
//            } else {
//                binding.viewError.root.visibility = View.VISIBLE
//            }
//        })
//    }


}