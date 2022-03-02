package com.anggarad.dev.foodfinder.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RegisterResponse
import com.anggarad.dev.foodfinder.core.domain.model.CurrentUserModel
import com.anggarad.dev.foodfinder.core.domain.model.LoginModel
import com.anggarad.dev.foodfinder.core.domain.model.RegisterModel
import com.anggarad.dev.foodfinder.core.domain.usecase.AuthUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _registerResponse = Channel<ApiResponse<RegisterResponse>>(Channel.BUFFERED)
    val registerResponse = _registerResponse.receiveAsFlow()

    suspend fun register(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): LiveData<Resource<RegisterModel>> {
        return authUseCase.userRegister(email, password, name, phoneNum, address).asLiveData()
//        viewModelScope.launch {
//            authUseCase.userRegister(email, password, name, phoneNum, address)
//                .catch { e ->
//                    _registerResponse.send(ApiResponse.Error(e.toString()))
//                }
//                .collect {
//                    _registerResponse.send(it)
//                }
//        }
    }

    suspend fun userLogin(email: String, password: String): LiveData<Resource<LoginModel>> {
        return authUseCase.userLogin(email, password).asLiveData()
    }


    fun saveCredential(token: String, userId: Int) {
        viewModelScope.launch {
            authUseCase.saveCredential(token, userId)
        }
    }

    fun saveUserData(userResponse: CurrentUserModel) {
        viewModelScope.launch {
            authUseCase.saveUserInfo(userResponse)
        }

    }
}