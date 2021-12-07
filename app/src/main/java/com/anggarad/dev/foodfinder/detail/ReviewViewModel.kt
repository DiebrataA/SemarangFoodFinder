package com.anggarad.dev.foodfinder.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.usecase.ReviewUseCase

class ReviewViewModel(private val reviewUseCase: ReviewUseCase) : ViewModel() {

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
