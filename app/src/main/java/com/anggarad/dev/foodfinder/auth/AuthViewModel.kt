package com.anggarad.dev.foodfinder.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.domain.usecase.AuthUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _loginResponse = Channel<ApiResponse<LoginResponse>>(Channel.BUFFERED)
    val loginResponse = _loginResponse.receiveAsFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.userLogin(email, password)
                .catch { e ->
                    _loginResponse.send(ApiResponse.Error(e.toString()))
                }
                .collect {
                    _loginResponse.send(it)
                }
        }
    }


    fun saveCredential(token: String, userId: Int) {
        viewModelScope.launch {
            authUseCase.saveCredential(token, userId)
        }
    }
}