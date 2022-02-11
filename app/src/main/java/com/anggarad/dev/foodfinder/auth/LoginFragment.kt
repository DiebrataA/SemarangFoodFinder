package com.anggarad.dev.foodfinder.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.databinding.FragmentLoginBinding
import com.anggarad.dev.foodfinder.home.HomeActivity
import kotlinx.coroutines.flow.collect
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

        binding.buttonLogin.isEnabled = true


        handleOnClick()


    }

    private fun handleOnClick() {
        binding.buttonLogin.setOnClickListener {
            setObservers()
        }

        binding.tbToRegister.setOnClickListener {
            val mRegisterFragment = RegisterFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.auth_container,
                    mRegisterFragment,
                    RegisterFragment::class.java.simpleName
                )
                commit()
            }
        }

    }

    private fun setObservers() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        authViewModel.login(email,password)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.loginResponse.collect { data ->
                when(data) {
                    is ApiResponse.Success -> {
                        val intent = Intent(activity, HomeActivity::class.java)
                        Toast.makeText(
                            requireContext(),
                            data.data.currUser.userId.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        authViewModel.saveCredential(data.data.token, data.data.currUser.userId)
                        startActivity(intent)

                    }
                    is ApiResponse.Error -> {
                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}