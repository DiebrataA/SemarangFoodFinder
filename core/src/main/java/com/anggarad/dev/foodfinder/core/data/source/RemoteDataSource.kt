package com.anggarad.dev.foodfinder.core.data.source

import android.util.Log
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiService
import com.anggarad.dev.foodfinder.core.data.source.remote.response.CurrUserItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.Feed
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getRestoList(): Flow<ApiResponse<List<ResponseItem>>> {
        return flow {
            try {
                val response = apiService.getAllResto()
                val restoArray = response.response
                if (restoArray.isNotEmpty()) {
                    emit(ApiResponse.Success(restoArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteData Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

//    suspend fun getCafeList(): Flow<ApiResponse<List<ResponseItem>>> {
//        return flow {
//            try {
//                val response = apiService.getCafes()
//                val restoArray = response.response
//                if (restoArray.isNotEmpty()) {
//                    emit(ApiResponse.Success(restoArray))
//                }
//            } catch (e: Exception) {
//                emit(ApiResponse.Error(e.toString()))
//                Log.e("Cafe Source", e.toString())
//            }
//        }.flowOn(Dispatchers.IO)
//    }

    suspend fun getUserDetail(userId: Int): Flow<ApiResponse<CurrUserItem>> {
        return flow {
            try {
                val response = apiService.getUserDetail(userId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
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