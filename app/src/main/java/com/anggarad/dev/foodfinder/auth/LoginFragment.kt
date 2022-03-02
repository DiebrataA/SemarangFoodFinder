package com.anggarad.dev.foodfinder.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.databinding.FragmentLoginBinding
import com.anggarad.dev.foodfinder.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.editEmail.addTextChangedListener(loginTextWatcher)
        binding.editPassword.addTextChangedListener(loginTextWatcher)
        handleOnClick()

    }

    private fun handleOnClick() {
        binding.buttonLogin.setOnClickListener {
            setObservers()
        }

        binding.tbToRegister.setOnClickListener {

            val mRegisterFragment = RegisterFragment()
            val mFragmentManager = requireActivity().supportFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.auth_container,
                    mRegisterFragment
                )
                addToBackStack(null)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                setReorderingAllowed(true)
                commit()
            }
        }

    }

    private fun setObservers() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
//        authViewModel.userLogin(email, password)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.userLogin(email, password).observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvErrorLogin.visibility = View.VISIBLE
                    }
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        val intent = Intent(activity, HomeActivity::class.java)
                        intent.putExtra(HomeActivity.USER_ID, response.data?.currentUser?.userId)
                        Toast.makeText(
                            requireContext(),
                            response.data?.currentUser?.userId.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        response.data?.let {
                            authViewModel.saveCredential(it.token, it.currentUser.userId)
                            authViewModel.saveUserData(it.currentUser)
                        }

                        startActivity(intent)
                        activity?.finish()
                    }
                }
            })
//            authViewModel.loginResponse.collect { data ->
//                when(data) {
//                    is ApiResponse.Success -> {
//                        val intent = Intent(activity, HomeActivity::class.java)
//                        intent.putExtra(HomeActivity.USER_ID, data.data.currUser.userId)
//                        Toast.makeText(
//                            requireContext(),
//                            data.data.currUser.userId.toString(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        authViewModel.saveCredential(data.data.token, data.data.currUser.userId)
//                        authViewModel.saveUserData(data.data.currUser)
//                        startActivity(intent)
//                        activity?.finish()
//
//                    }
//                    is ApiResponse.Error -> {
//                        binding.tvErrorLogin.visibility = View.VISIBLE
//                    }
//                }
//            }
        }
    }

    private val loginTextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            binding.buttonLogin.isEnabled = email.isNotEmpty() && password.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.finish()
    }


}




