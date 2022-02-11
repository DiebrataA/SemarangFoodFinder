package com.anggarad.dev.foodfinder.core.data.source.local.room

import androidx.room.*
import androidx.room.Dao
import com.anggarad.dev.foodfinder.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    //User
    @Query("SELECT * FROM user WHERE user_Id=:userId")
    fun getUser(userId: Int): Flow<UserDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDetailEntity)

    //RESTO
    @Query("SELECT * FROM restos")
    fun getAllRestos(): Flow<List<RestoEntity>>

    @Query("SELECT * FROM restos WHERE restoId = :restoId")
    fun getRestoDetail(restoId: Int): Flow<RestoEntity>

    @Query("SELECT * FROM restos where isFavorite = 1")
    fun getFavoriteRestos(): Flow<List<RestoEntity>>

    @Query("SELECT * FROM restos_menu WHERE restoId = :restoId")
    fun getMenuRestos(restoId: Int): Flow<List<MenuEntity>>

    @Query("SELECT * FROM search WHERE name LIKE :query")
    fun getAllSearchHistory(query: String): Flow<List<SearchItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestos(restoEntity: List<RestoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menuEntity: List<MenuEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchItem(searchItemEntity: List<SearchItemEntity>)

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