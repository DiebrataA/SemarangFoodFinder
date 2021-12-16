package com.anggarad.dev.foodfinder.core.data.source

import android.util.Log
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiService
import com.anggarad.dev.foodfinder.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getRestoReviews(restoId: Int): Flow<ApiResponse<List<ReviewItem>>> {
        return flow {
            try {
                val response = apiService.getRestoReviews(restoId)

                val reviewsArray = response.response
                if (reviewsArray !== null && reviewsArray.isNotEmpty()) {
                    emit(ApiResponse.Success(reviewsArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUsersReviews(userId: Int): Flow<ApiResponse<List<ReviewItem>>> {
        return flow {
            try {
                val response = apiService.getUserReviews(userId)

                val reviewsArray = response.response
                if (reviewsArray !== null && reviewsArray.isNotEmpty()) {
                    emit(ApiResponse.Success(reviewsArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postReviews(
        restoId: Int,
        userId: Int,
        rating: Double,
        comments: String,
        fileName: String,
        body: RequestBody
    ): Flow<ApiResponse<ReviewResponse>> {
        val requestRestoId =
            restoId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val requestUserId =
            userId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val requestRating =
            rating.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val requestComments = comments.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val requestFile = MultipartBody.Part.createFormData("img_review_path", fileName, body)

        return flow {
            try {
                val response = apiService.postReview(
                    requestRestoId,
                    requestUserId,
                    requestRating,
                    requestComments,
                    requestFile
                )
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }


    }

    suspend fun getRestoList(): Flow<ApiResponse<List<RestoItems>>> {
        return flow {
            try {
                val response = apiService.getAllResto()

                val restoArray = response.response
                if (restoArray.isNotEmpty()) {
                    emit(ApiResponse.Success(restoArray))
                    Log.d("User Response: ", restoArray.toString())
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))

            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCafeList(): Flow<ApiResponse<List<RestoItems>>> {
        return flow {
            try {
                val response = apiService.getCafes()
                val restoArray = response.response
                if (restoArray.isNotEmpty()) {
                    emit(ApiResponse.Success(restoArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("Cafe Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(userId: Int?): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response = apiService.getUserDetail(userId)
                emit(ApiResponse.Success(response))
                Log.d("User Response: ", response.toString())
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))

            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun userLogin(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val response = apiService.userLogin(email, password)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("LoginError :", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }


}