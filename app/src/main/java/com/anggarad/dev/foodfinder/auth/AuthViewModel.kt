package com.anggarad.dev.foodfinder.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.domain.usecase.UserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(private val userUseCase: UserUseCase): ViewModel() {

    private val _loginResponse= Channel<ApiResponse<LoginResponse>>(Channel.BUFFERED)
    val loginResponse = _loginResponse.receiveAsFlow()

    fun login(email:String, password: String) {
        viewModelScope.launch {
            userUseCase.userLogin(email, password)
                .catch { e ->
                    _loginResponse.send(ApiResponse.Error(e.toString()))
                }
                .collect {
                    _loginResponse.send(it)
                }
        }
    }



    fun saveCredential( token: String) {
        viewModelScope.launch {
            userUseCase.saveCredential(token)
        }
    }
}