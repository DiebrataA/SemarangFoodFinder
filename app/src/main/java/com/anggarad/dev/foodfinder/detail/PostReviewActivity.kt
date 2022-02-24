package com.anggarad.dev.foodfinder.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggarad.dev.foodfinder.R
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.utils.UploadRequestBody
import com.anggarad.dev.foodfinder.core.utils.getFileName
import com.anggarad.dev.foodfinder.databinding.ActivityPostReviewBinding
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class PostReviewActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {

    companion object {
        const val EXTRA_DATA = "extra_data"
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }

    private val reviewViewModel: ReviewViewModel by viewModel()
    private lateinit var binding: ActivityPostReviewBinding
    private var ratingResult: Float = 0.0f
    private var imageToProcess: Uri? = null
    private var requestBody: RequestBody? = null
    private var imageUrl: String? = null

    private var restoId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        restoId = intent.getIntExtra(EXTRA_DATA, 0)

        binding.ratingBar.stepSize = .5f
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingResult = rating
        }

        binding.imageButton2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                openImageChooser()
            }

        }

        binding.btnSubmitReview.setOnClickListener {
            handlePostReview(restoId)
        }

    }

//    private fun uploadReview(){
//
//        val parcelFileDescriptor = contentResolver.openFileDescriptor(imageToProcess!!, "r", null) ?: return
//        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//        file = File(cacheDir, contentResolver.getFileName(imageToProcess!!))
//        val outputStream = FileOutputStream(file)
//        inputStream.copyTo(outputStream)
//
//        binding.progressBarUploadImage.progress = 0
//        val body = UploadRequestBody(file!!, "image", this)
//    }

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
                requestBody = UploadRequestBody(file, "image", this)
//                        requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            }
        }

    private fun uploadImageFirebase() {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString: String = List(20) { alphabet.random() }.joinToString("")
        reviewViewModel.postImage(
            imageToProcess!!,
            "$randomString",
            "images",
            "reviewPicture"
        ).observe(
            this, { downloadUrl ->
                if (downloadUrl != null) {
                    imageUrl = downloadUrl
                    binding.progressBarUploadImage.progress = 100
                }
            }
        )
    }


    private fun handlePostReview(restoId: Int?) {


        reviewViewModel.userId.observe(this, { userId ->
            reviewViewModel.userToken.observe(this, { token ->

                val comments = binding.editComments.text.toString().trim()
                val commentsB = comments.toRequestBody("text/plain".toMediaTypeOrNull())


                val fileName = binding.tvFilename.text.toString()

                restoId?.let {
                    imageUrl?.let { it1 ->
                        reviewViewModel.postReview(
                            "Bearer $token",
                            it,
                            userId,
                            ratingResult,
                            comments,
                            it1
                        )
                    }

                }

                lifecycleScope.launchWhenStarted {
                    reviewViewModel.reviewResponse.collect { data ->
                        when (data) {
                            is ApiResponse.Success -> {
                                Toast.makeText(
                                    this@PostReviewActivity,
                                    "Post Berhasil",
                                    Toast.LENGTH_SHORT
                                ).show()
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