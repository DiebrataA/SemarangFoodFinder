package com.anggarad.dev.foodfinder.core.data

import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {

        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emitAll(collectResult(apiResponse.data).map { Resource.Success(it) })
            }
            is ApiResponse.Empty<*> -> {
                emitAll(collectResult(apiResponse.data as RequestType).map { Resource.Success(it) })
            }
            is ApiResponse.Error -> {
                onFetchFailed()
                emit(Resource.Error<ResultType>(apiResponse.errorMessage))
            }
        }

//        else {
//            emitAll(loadFromDB().map { Resource.Success(it) })
//        }
    }

    protected open fun onFetchFailed() {}


    protected abstract fun collectResult(data: RequestType): Flow<ResultType>


    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>


    fun asFlow(): Flow<Resource<ResultType>> = result
}