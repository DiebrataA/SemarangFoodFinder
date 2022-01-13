package com.anggarad.dev.foodfinder.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.databinding.FragmentRegisterBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(), View.OnClickListener {
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var binding: FragmentRegisterBinding

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


    private fun setObservers() {

        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
        val name = binding.editFullname.text.toString().trim()
        val phoneNum = binding.editPhone.text.toString().trim()
        val address = binding.editAddress.text.toString().trim()

        authViewModel.register(email, password, name, phoneNum, address)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.registerResponse.collect { data ->
                when (data) {
                    is ApiResponse.Success -> {
                        displayLoginFragment()
                    }
                    is ApiResponse.Error -> {
                        Toast.makeText(requireContext(), "Register Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun displayLoginFragment() {
        val mLoginFragment = LoginFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(R.id.auth_container, mLoginFragment, LoginFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_register) {
            setObservers()
        }

    }


}