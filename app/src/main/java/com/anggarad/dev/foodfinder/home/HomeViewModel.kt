package com.anggarad.dev.foodfinder.home


import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.usecase.CategoriesUseCase
import com.anggarad.dev.foodfinder.core.domain.usecase.RestoUseCase

class HomeViewModel(
    private val restoUseCase: RestoUseCase,
    categoriesUseCase: CategoriesUseCase,
) : ViewModel() {
    val location = MutableLiveData<Location>()
    fun getRestolist(pullToRefresh: Boolean): LiveData<Resource<List<RestoDetail>>> {
        return restoUseCase.getRestoList(pullToRefresh).asLiveData()
    }

    fun getLocation(loc: Location) {
        location.value = loc
    }

    //    val getCafelist = restoUseCase.getCafeList().asLiveData()
    val getCategories = categoriesUseCase.getRestoCategories().asLiveData()

}