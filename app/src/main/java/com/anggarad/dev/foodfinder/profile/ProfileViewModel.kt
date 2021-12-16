package com.anggarad.dev.foodfinder.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.UserResponse
import com.anggarad.dev.foodfinder.core.domain.usecase.UserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _userResponse = Channel<ApiResponse<UserResponse>>(Channel.BUFFERED)
    val userResponse = _userResponse.receiveAsFlow()

    fun getUSerDetail(userId: Int) {
        viewModelScope.launch {
            userUseCase.getUserDetail(userId)
                .catch { e ->
                    _userResponse.send(ApiResponse.Error(e.toString()))
                }
                .collect {
                    _userResponse.send(it)
                }
        }
    }


    val userId = userUseCase.getUserId().asLiveData()

//    fun getUserDetail(userId: Int): LiveData<Resource<UserDetail>> {
//        return userUseCase.getUserDetail(userId).asLiveData()
//    }
//    val getUserDetail = userUseCase.getUserDetail( 7).asLiveData()

}