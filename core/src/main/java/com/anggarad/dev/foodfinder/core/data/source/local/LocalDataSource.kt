package com.anggarad.dev.foodfinder.core.data.source.local

import com.anggarad.dev.foodfinder.core.data.source.local.entity.*
import com.anggarad.dev.foodfinder.core.data.source.local.room.Dao
import kotlinx.coroutines.flow.Flow


class LocalDataSource(private val dao: Dao) {


    suspend fun insertResto(restolist: List<RestoEntity>) = dao.insertRestos(restolist)

    fun getRestoListData(): Flow<List<RestoEntity>> = dao.getAllRestos()

    fun getRestoByCategory(id: Int): Flow<List<RestoByCategoryEntity>> =
        dao.getRestosByCategoryId(id)

    fun getRestoDetail(restoId: Int): Flow<RestoEntity> = dao.getRestoDetail(restoId)

    fun getFavoriteResto(): Flow<List<RestoEntity>> = dao.getFavoriteRestos()

    fun getRestoCategories(): Flow<List<CategoriesEntity>> = dao.getRestoCategories()

    suspend fun insertRestoCategories(categoriesEntity: List<CategoriesEntity>) =
        dao.insertCategories(categoriesEntity)

    suspend fun insertRestoByCategory(restoByCategoryEntity: List<RestoByCategoryEntity>) =
        dao.insertRestosByCategory(restoByCategoryEntity)

    fun getAllSearchHistory(query: String): Flow<List<SearchItemEntity>> =
        dao.getAllSearchHistory(query)

    suspend fun insertSearch(searchItemEntity: List<SearchItemEntity>) =
        dao.insertSearchItem(searchItemEntity)

    suspend fun insertMenu(menuList: List<MenuEntity>) = dao.insertMenu(menuList)
    fun getMenuResto(restoId: Int): Flow<List<MenuEntity>> = dao.getMenuRestos(restoId)

    fun getUserData(userId: Int): Flow<UserDetailEntity> = dao.getUser(userId)

    suspend fun insertUserData(user: UserDetailEntity) = dao.insertUser(user)

    suspend fun insertSingleResto(resto: RestoEntity) = dao.insertSingleRestos(resto)

    fun setFavoriteResto(resto: RestoEntity, newState: Boolean) {
        resto.isFavorite = newState
        dao.updateRestoData(resto)
    }

    suspend fun insertReview(reviewList: List<ReviewEntity>) = dao.insertReviews(reviewList)

    fun getRestoReviews(restoId: Int): Flow<List<ReviewEntity>> = dao.getAllReviews(restoId)

    fun getUserReviews(userId: Int): Flow<List<ReviewEntity>> = dao.getUsersReview(userId)
}