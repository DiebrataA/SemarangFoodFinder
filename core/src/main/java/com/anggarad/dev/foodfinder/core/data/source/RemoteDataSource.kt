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


class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getRestoReviews(restoId: Int): Flow<ApiResponse<List<ReviewItem>>> {
        return flow {
            try {
                val response = apiService.getRestoReviews(restoId)

                val reviewsArray = response.response
                if (reviewsArray !== null && reviewsArray.isNotEmpty()) {
                    emit(ApiResponse.Success(reviewsArray))
                } else if (reviewsArray !== null && reviewsArray.isEmpty()) {
                    emit(ApiResponse.Empty)
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
        token: String,
        restoId: Int,
        userId: Int,
        rating: Float,
        comments: String,
        fileName: String,
        body: RequestBody?
    ): Flow<ApiResponse<PostReviewResponse>> {

        val requestRestoId = RequestBody.create(MultipartBody.FORM, "1")
        //restoId.toString().toRequestBody(MultipartBody.FORM)

        val requestUserId = RequestBody.create("text/plain".toMediaTypeOrNull(), "1")
//            MultipartBody.Builder().addFormDataPart("user_id", userId.toString()).build()

        val requestRating = RequestBody.create("text/plain".toMediaTypeOrNull(), "4")
//            rating.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val requestComments = RequestBody.create("text/plain".toMediaTypeOrNull(), comments)
//        val commentsB = MultipartBody.Part.createFormData("comments", comments)

//        val partMap = HashMap<String, RequestBody>()
//        partMap.put("resto_id", requestRestoId)
//        partMap.put("user_id", requestUserId)
//        partMap.put("rating", requestRating)
//        partMap.put("comments", requestComments)

        val image =
            body?.let { MultipartBody.Part.createFormData("img_review_path", fileName, body) }
        return flow {
            try {
                val response = apiService.postReview(
                    token,
                    requestRestoId,
                    requestUserId,
                    requestRating,
                    requestComments,
                    image
                )
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)


    }

    suspend fun getRestoList(): Flow<ApiResponse<List<RestoItems>>> {
        return flow {
            try {
                val response = apiService.getAllResto()

                val restoArray = response.response
                if (restoArray.isNotEmpty()) {
                    emit(ApiResponse.Success(restoArray))

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

            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRestoMenu(restoId: Int): Flow<ApiResponse<List<MenuResponseItem>>> {
        return flow {
            try {
                val response = apiService.getRestoMenu(restoId)
                val menuArray = response.response
                if (menuArray.isNotEmpty()) {
                    emit(ApiResponse.Success(menuArray))
                } else if (menuArray !== null && menuArray.isEmpty()) {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchResto(key: String): Flow<ApiResponse<List<SearchItem>>> {
        return flow {
            try {
                val response = apiService.searchResto(key)
                val searchResArray = response.response
                if (searchResArray.isNotEmpty()) {
                    emit(ApiResponse.Success(searchResArray))
                } else if (searchResArray !== null && searchResArray.isEmpty()) {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
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

    suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<ApiResponse<RegisterResponse>> {
        return flow {
            try {
                val response = apiService.userRegister(name, address, phoneNum, email, password)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }


}