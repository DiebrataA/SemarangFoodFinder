package com.anggarad.dev.foodfinder.core.data.source.local

import com.anggarad.dev.foodfinder.core.data.source.local.entity.RestoEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.UserDetailEntity
import com.anggarad.dev.foodfinder.core.data.source.local.room.Dao
import com.anggarad.dev.foodfinder.core.data.source.remote.response.CurrUserItem
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import kotlinx.coroutines.flow.Flow


class LocalDataSource(private val dao: Dao) {

    suspend fun insertResto(restolist: List<RestoEntity>)= dao.insertRestos(restolist)

    fun getRestoListData(): Flow<List<RestoEntity>> = dao.getAllRestos()

    fun getFavoriteResto(): Flow<List<RestoEntity>> = dao.getFavoriteRestos()

    fun getUserData(userId: Int): Flow<UserDetailEntity> = dao.getUser(userId)

    suspend fun insertUserData(user: UserDetailEntity) = dao.insertUser(user)

    fun setFavoriteResto(resto: RestoEntity, newState: Boolean) {
        resto.isFavorite = newState
        dao.updateFavoriteRestos(resto)
    }
}