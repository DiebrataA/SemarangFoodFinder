package com.anggarad.dev.foodfinder.core.data.source.local.room

import androidx.room.*
import androidx.room.Dao

import com.anggarad.dev.foodfinder.core.data.source.local.entity.RestoEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.ReviewEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.UserDetailEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    //User
    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUser(userId:Int): Flow<UserDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDetailEntity)

    //RESTO
    @Query("SELECT * FROM restos")
    fun getAllRestos(): Flow<List<RestoEntity>>

    @Query("SELECT * FROM restos where isFavorite = 1")
    fun getFavoriteRestos(): Flow<List<RestoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestos(restoEntity: List<RestoEntity>)

    @Update
    fun updateFavoriteRestos(resto: RestoEntity)

    //REVIEWS
    @Query("SELECT * FROM reviews WHERE resto_id = :restoId")
    fun getAllReviews(restoId: Int): Flow<List<ReviewEntity>>

    @Query("SELECT * FROM reviews WHERE user_id = :userId")
    fun getUsersReview(userId: Int): Flow<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviewEntity: List<ReviewEntity>)
}