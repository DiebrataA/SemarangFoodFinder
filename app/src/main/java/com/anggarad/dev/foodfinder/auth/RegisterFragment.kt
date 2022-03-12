package com.anggarad.dev.foodfinder.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserRegister
import com.anggarad.dev.foodfinder.databinding.FragmentRegisterBinding
import com.anggarad.dev.foodfinder.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(), View.OnClickListener {
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var binding: FragmentRegisterBinding
//    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRegister.isEnabled = true
        binding.buttonRegister.setOnClickListener(this)
    }

    private fun setObserversFb() {

        val email = binding.editEmailRegister.text.toString().trim()
        val password = binding.editPasswordRegister.text.toString().trim()
        val name = binding.editFullname.text.toString().trim()
        val phoneNum = binding.editPhone.text.toString().trim()
        val address = binding.editAddress.text.toString().trim()



        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.register(email, password, name, phoneNum, address)
                .observe(viewLifecycleOwner, { response ->
                    val userId = response.data?.userId
                    val accId = response.data?.accId
                    val userReg = UserRegister(userId, accId, name, address, phoneNum, email)
                    when (response) {
                        is Resource.Success -> {
                            authViewModel.registerWithEmailFb(email, password, userReg)
                                .observe(viewLifecycleOwner, { responseFb ->
                                    when (responseFb) {
                                        is Resource.Error -> {
                                            binding.progressBar.visibility = View.GONE
                                            Toast.makeText(
                                                requireContext(),
                                                "Register Failed",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        is Resource.Success -> {
                                            binding.progressBar.visibility = View.GONE
                                            Toast.makeText(
                                                requireContext(),
                                                "Register Success",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                            responseFb.data?.userId?.let {
                                                authViewModel.saveCredential(
                                                    it
                                                )
                                                Log.d("UserID:", it.toString())
                                            }
                                            displayHome()
                                            activity?.finish()
                                        }
                                    }
                                })
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Cannot Write to DB",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    }
                })
        }
    }

//    private fun displayLoginFragment() {
//        val mLoginFragment = LoginFragment()
//        val mFragmentManager = requireActivity().supportFragmentManager
//        mFragmentManager.beginTransaction().apply {
//            replace(R.id.auth_container, mLoginFragment, LoginFragment::class.java.simpleName)
//            addToBackStack(null)
//            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            commit()
//        }
//    }

    private fun displayHome() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_register) {
            setObserversFb()
        }

    }


}