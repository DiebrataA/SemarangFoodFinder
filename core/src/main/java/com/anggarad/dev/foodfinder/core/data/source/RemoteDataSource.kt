package com.anggarad.dev.foodfinder.core.data.source

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiService
import com.anggarad.dev.foodfinder.core.data.source.remote.network.FirebaseService
import com.anggarad.dev.foodfinder.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(private val apiService: ApiService) {

    private val firebaseService: FirebaseService = FirebaseService()

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
        imgReviewPath: String
    ): Flow<ApiResponse<PostReviewResponse>> {

        return flow {
            try {
                val response =
                    apiService.postReview(token, restoId, userId, rating, comments, imgReviewPath)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("PostReviewError :", e.toString())
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

    suspend fun getRestoDetail(restoId: Int): Flow<ApiResponse<SingleRestoItem>> {
        return flow {
            try {
                val response = apiService.getRestoDetails(restoId)
                val restoItem = response.values.singleRestoItem
                emit(ApiResponse.Success(restoItem))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRestoByCategoryList(restoId: Int): Flow<ApiResponse<List<RestoByCategoryItems>>> {
        return flow {
            try {
                val response = apiService.getRestoByCategory(restoId)
                val restoArray = response.response
                if (restoArray.isNotEmpty()) {
                    emit(ApiResponse.Success(restoArray))
                } else {
                    emit(ApiResponse.Empty)
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

    suspend fun getUserDetail(userId: Int): Flow<ApiResponse<UserResponse>> {
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

    fun postImage(uri: Uri, uid: String, type: String, name: String): LiveData<String> {
        return firebaseService.uploadImage(uri, uid, type, name)
    }

    fun getRestoCategories(): Flow<ApiResponse<List<CategoriesResponseItem>>> {
        return flow {
            try {
                val response = apiService.getRestoCategories()
                val categoriesArray = response.response
                if (categoriesArray.isNotEmpty()) {
                    emit(ApiResponse.Success(categoriesArray))
                } else if (categoriesArray !== null && categoriesArray.isEmpty()) {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }


}