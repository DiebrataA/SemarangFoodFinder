package com.anggarad.dev.foodfinder.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.PostReviewResponse
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.usecase.ReviewUseCase
import com.anggarad.dev.foodfinder.core.domain.usecase.UserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewUseCase: ReviewUseCase,
    userUseCase: UserUseCase
) : ViewModel() {
    private val _reviewResponse = Channel<ApiResponse<PostReviewResponse>>(Channel.BUFFERED)
    val reviewResponse = _reviewResponse.receiveAsFlow()

    fun postReview(
        restoId: Int,
        userId: Int,
        rating: Float,
        comments: String,
        imgReviewPath: String
    ) {
        viewModelScope.launch {
            reviewUseCase.postReview(restoId, userId, rating, comments, imgReviewPath)
                .catch { e ->
                    _reviewResponse.send(ApiResponse.Error(e.toString()))
                }
                .collect {
                    _reviewResponse.send(it)
                }
        }

    }

    fun postImage(uri: Uri, uid: String, type: String, name: String): LiveData<Resource<String>> {
        return reviewUseCase.postImage(uri, uid, type, name)
    }

    val userId = userUseCase.getUserId().asLiveData()
    val userToken = reviewUseCase.getToken().asLiveData()

//    private val _reviewList =MutableSharedFlow<Resource<List<ReviewDetails>>>()
//    val reviewList = _reviewList.asSharedFlow()
//
//    fun getRestoReviews(restoId: Int) {
//        viewModelScope.launch {
//            _reviewList.emit(reviewUseCase.getRestoReviews(restoId).first())
//        }
//    }

    fun getRestoReviews(restoId: Int): LiveData<Resource<List<ReviewDetails>>> {
        return reviewUseCase.getRestoReviews(restoId).asLiveData()
    }

}
