package com.anggarad.dev.foodfinder.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserRegister
import com.anggarad.dev.foodfinder.databinding.FragmentLoginBinding
import com.anggarad.dev.foodfinder.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val fDb: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var fAuth: FirebaseAuth

    companion object {
        const val RC_SIGN_IN = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        fAuth = Firebase.auth
    }

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
        continueWithGoogle()

    }

    private fun handleOnClick() {
        binding.buttonLogin.setOnClickListener {
            setObserversFbEmail()
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

    private val loginTextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            binding.buttonLogin.isEnabled = email.isNotEmpty() && password.isNotEmpty()
            if (binding.tvErrorLogin.visibility == View.VISIBLE) {
                binding.tvErrorLogin.visibility = View.GONE
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.finish()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun continueWithGoogle() {
        binding.btnLoginGoogle.setOnClickListener {
            signIn()

        }
    }

    private fun setObserversFbEmail() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        authViewModel.loginWithEmailFb(email, password).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    val intent = Intent(activity, HomeActivity::class.java)
                    Toast.makeText(
                        requireContext(),
                        response.data?.fullName,
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(intent)
                    response.data?.userId?.let { authViewModel.saveCredential(it) }
                    activity?.finish()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvErrorLogin.visibility = View.VISIBLE
                    binding.editEmail.error
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        authViewModel.continueWithGoogle(idToken).observe(viewLifecycleOwner, { task ->
            val name = task.data?.fullName
            val imgProfile = task.data?.imgProfile
            val email = task.data?.email
            val phoneNum = task.data?.phoneNum
            val password = ""
            val address = ""

            when (task) {
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        task.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    if (task.data?.isNew == true) {
                        lifecycleScope.launchWhenStarted {
                            authViewModel.register(email,
                                password,
                                name,
                                phoneNum,
                                address,
                                imgProfile).observe(viewLifecycleOwner, { response ->
                                val userInfo = UserRegister(
                                    userId = response.data?.userId,
                                    accId = response.data?.accId,
                                    fullName = name,
                                    email = email,
                                    address = address,
                                    phoneNum = phoneNum,
                                    imgProfile = imgProfile
                                )
                                when (response) {
                                    is Resource.Success -> {
                                        userInfo.userId?.let { authViewModel.saveCredential(it) }
                                        val userRef = fDb.getReference("Users")
                                        fAuth.currentUser?.let {
                                            userRef.child(it.uid).setValue(userInfo)
                                        }
                                        val intent = Intent(activity, HomeActivity::class.java)
                                        Toast.makeText(
                                            requireContext(),
                                            "Welcome ${userInfo.userId}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        startActivity(intent)
                                    }

                                    is Resource.Error -> Toast.makeText(
                                        requireContext(),
                                        "Gagal Write To RTDB",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                        }
                    } else {
                        binding.progressBar.visibility = View.VISIBLE
                        val uid = fAuth.currentUser?.uid
                        uid?.let {

                            fDb.getReference("Users").child(it).child("userId").get()
                                .addOnSuccessListener { task ->
                                    val usId = task.value.toString().toInt()
                                    authViewModel.saveCredential(usId)
                                    Toast.makeText(
                                        requireContext(),
                                        "User terdaftar",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    binding.progressBar.visibility = View.GONE
                                    val intent = Intent(activity, HomeActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()
                                }
                        }

                    }


                }
            }
        })
    }


}




