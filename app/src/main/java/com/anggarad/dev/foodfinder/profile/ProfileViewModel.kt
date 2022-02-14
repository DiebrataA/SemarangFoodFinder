package com.anggarad.dev.foodfinder.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.usecase.UserUseCase

class ProfileViewModel(private val userUseCase: UserUseCase) : ViewModel() {

//    private val _userResponse = Channel<ApiResponse<UserResponse>>(Channel.BUFFERED)
//    val userResponse = _userResponse.receiveAsFlow()
//
//    fun getUSerDetail(userId: Int) {
//        viewModelScope.launch {
//            userUseCase.getUserDetail(userId)
//                .catch { e ->
//                    _userResponse.send(ApiResponse.Error(e.toString()))
//                }
//                .collect {
//                    _userResponse.send(it)
//                }
//        }
//    }


    val userId = userUseCase.getUserId().asLiveData()

    fun getUserReviews(userId: Int): LiveData<Resource<List<ReviewDetails>>> {
        return userUseCase.getUsersReview(userId).asLiveData()
    }

    fun getUserDetail(userId: Int): LiveData<Resource<UserDetail>> {
        return userUseCase.getUserDetail(userId).asLiveData()
    }

//    fun getUserData(userId: Int): LiveData<UserDetail>{
//
//    }
//    val getUserDetail = userUseCase.getUserDetail( 7).asLiveData()

}