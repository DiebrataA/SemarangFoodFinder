package com.anggarad.dev.foodfinder.reviews

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.utils.UploadRequestBody
import com.anggarad.dev.foodfinder.core.utils.getFileName
import com.anggarad.dev.foodfinder.databinding.ActivityPostReviewBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class PostReviewActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val reviewViewModel: ReviewViewModel by viewModel()
    private lateinit var binding: ActivityPostReviewBinding
    private var ratingResult: Float = 0.0f
    private var imageToProcess: Uri? = null
    private var imageUrl: String? = null
    private var restoId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        restoId = intent.getIntExtra(EXTRA_DATA, 0)
        binding.btnSubmitReview.isEnabled = false
        binding.editComments.addTextChangedListener(reviewTextWatcher)

        binding.ratingBar.stepSize = 1f
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingResult = rating
            binding.btnSubmitReview.isEnabled = true
        }

        binding.imageButton2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                openImageChooser()
            }

        }

        reviewViewModel.userId.observe(this, {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        })

        binding.btnSubmitReview.setOnClickListener {
            handlePostReview(restoId)
        }

    }

    private val reviewTextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            binding.btnSubmitReview.isEnabled = false
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val comments = binding.editComments.text.toString().trim()
            binding.btnSubmitReview.isEnabled = comments.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {
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

                binding.imageButton2.setImageURI(imageToProcess)
                uploadImageFirebase()
                binding.tvFilename.text = file.name
                binding.progressBarUploadImage.progress = 0
                binding.btnSubmitReview.isEnabled = false
            }
        }

    private fun uploadImageFirebase() {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString: String = List(20) { alphabet.random() }.joinToString("")
        reviewViewModel.postImage(
            imageToProcess!!,
            "reviewPic/$randomString",
            "images",
            "reviewPicture"
        ).observe(
            this, { downloadUrl ->
                when (downloadUrl) {
                    is Resource.Loading -> {
                        binding.btnSubmitReview.isEnabled = false
                        onProgressUpdate(50)
                    }
                    is Resource.Success -> {
                        binding.btnSubmitReview.isEnabled = true
                        imageUrl = downloadUrl.data
                        onProgressUpdate(100)
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            this@PostReviewActivity,
                            "Gagal upload foto",
                            Toast.LENGTH_SHORT
                        ).show()
                        onProgressUpdate(0)
                    }
                }
            }
        )
    }


    private fun handlePostReview(restoId: Int?) {

        reviewViewModel.userId.observe(this, { userId ->
            val comments = binding.editComments.text.toString().trim()
            restoId?.let {

                    reviewViewModel.postReview(
                        it,
                        userId,
                        ratingResult,
                        comments,
                        imageUrl ?: null.toString()
                    )


            }

            lifecycleScope.launchWhenStarted {
                reviewViewModel.reviewResponse.collect { data ->
                    when (data) {
                        is ApiResponse.Success -> {
                            Toast.makeText(
                                this@PostReviewActivity,
                                "Your Review Has been Submitted",
                                Toast.LENGTH_SHORT
                            ).show()
                            onBackPressed()
                            finish()
                        }
                        is ApiResponse.Error -> {
                            Toast.makeText(
                                this@PostReviewActivity,
                                "Post Gagal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }


        })

    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimetypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        }
        getResult.launch(intent)
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBarUploadImage.progress = percentage
    }
}