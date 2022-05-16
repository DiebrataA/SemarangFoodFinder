package com.anggarad.dev.foodfinder.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.utils.UploadRequestBody
import com.anggarad.dev.foodfinder.core.utils.getFileName
import com.anggarad.dev.foodfinder.databinding.ActivityEditProfileBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class EditProfileActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhone: EditText
    private lateinit var userDetail: UserDetail
    private var imageToProcess: Uri? = null
    private var imageUrl: String? = null
    private var imageProfile: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etName = binding.editProfileFullname
        etAddress = binding.editProfileAddress
        etPhone = binding.editProfilePhone
        etEmail = binding.editProfileEmail

        binding.btnChangePicture.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                openImageChooser()
            }

        }

        val uid = intent.getStringExtra(USER_ID)
        uid?.let { id ->
            fetchUserData(id)
            binding.btnUpdateProfile.setOnClickListener {
                updateUserData(id)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openImageChooser()
            } else {
                Toast.makeText(this, "No Gallery Access", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimetypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        }
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {

                imageToProcess = it.data?.data


                val parcelFileDescriptor =
                    contentResolver.openFileDescriptor(imageToProcess!!, "r", null)
                        ?: return@registerForActivityResult
                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val file = File(cacheDir, contentResolver.getFileName(imageToProcess!!))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)

                binding.editProfileAvatar.setImageURI(imageToProcess)
                uploadImageFirebase()
                binding.progressBarUpload.progress = 0
                binding.btnUpdateProfile.isEnabled = false
            }
        }

    private fun uploadImageFirebase() {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString: String = List(20) { alphabet.random() }.joinToString("")
        profileViewModel.postImageProfile(
            imageToProcess!!,
            "profPic/$randomString",
            "images",
            "profilePicture"
        ).observe(
            this, { downloadUrl ->
                when (downloadUrl) {
                    is Resource.Loading -> {
                        binding.btnUpdateProfile.isEnabled = false
                        onProgressUpdate(50)
                    }
                    is Resource.Success -> {
                        binding.btnUpdateProfile.isEnabled = true
                        imageUrl = downloadUrl.data
                        onProgressUpdate(100)
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            this,
                            "Gagal upload foto",
                            Toast.LENGTH_SHORT
                        ).show()
                        onProgressUpdate(0)
                    }
                }
            }
        )
    }

    private fun fetchUserData(uid: String) {

        profileViewModel.fetchUserDetail(uid).observe(this, { user ->
            when (user) {
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Cannot Fetch User Data", Toast.LENGTH_SHORT).show()
//                    binding.viewError.root.visibility = View.VISIBLE
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {

                    binding.progressBar.visibility = View.GONE
                    user.data?.let {
                        userDetail = it
                        imageProfile = it.imgProfile
                        etName.setText(it.fullName)
                        etEmail.setText(it.email)
                        etPhone.setText(it.phoneNum)
                        etAddress.setText(it.address)
                        Glide.with(this)
                            .load(imageProfile)
                            .into(binding.editProfileAvatar)
                    }
                }
            }
        })

    }


    private fun updateUserData(uid: String) {
        val name = etName.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val userId = userDetail.userId
        val accId = userDetail.accId
        val profPic = imageUrl ?: imageProfile

        val userEditInfo = UserDetail(
            userId = userId,
            accId = accId,
            fullName = name,
            address = address,
            imgProfile = profPic,
            phoneNum = phone,
            email = email

        )

        profileViewModel.editProfile(uid, userEditInfo).observe(this, { editUser ->
            when (editUser) {
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Cannot Update", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    lifecycleScope.launch {
                        profileViewModel.editUserDataDb(userId, name, address, phone, profPic)
                            .observe(this@EditProfileActivity, { writToDb ->
                                when (writToDb) {
                                    is Resource.Error -> {
                                        binding.progressBar.visibility = View.GONE
                                        Toast.makeText(this@EditProfileActivity,
                                            "Cannot Write To Db",
                                            Toast.LENGTH_SHORT).show()
                                    }

                                    is Resource.Success -> {
                                        binding.progressBarUpload.visibility = View.INVISIBLE
                                        Toast.makeText(this@EditProfileActivity,
                                            "Update Success",
                                            Toast.LENGTH_SHORT).show()
                                        onBackPressed()
                                    }
                                }
                            })
                    }
                }
            }
        })
    }

    companion object {
        const val USER_ID = "uid"
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBarUpload.visibility = View.VISIBLE
        binding.progressBarUpload.progress = percentage
    }
}
