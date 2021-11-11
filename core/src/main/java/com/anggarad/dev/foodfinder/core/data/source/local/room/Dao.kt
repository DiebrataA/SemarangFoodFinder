package com.anggarad.dev.foodfinder.core.data.source.local.room

import androidx.room.*
import androidx.room.Dao
import com.anggarad.dev.foodfinder.core.data.source.local.entity.RecipeDetailEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.UserDetailEntity
import com.anggarad.dev.foodfinder.core.data.source.remote.response.CurrUserItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    //User
    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUser(userId:Int): Flow<UserDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDetailEntity)

    @Query("SELECT * FROM recipe")
    fun getAllRecipe(): Flow<List<RecipeDetailEntity>>

    @Query("SELECT * FROM recipe where isFavorite = 1")
    fun getFavoriteRecipe(): Flow<List<RecipeDetailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: List<RecipeDetailEntity>)

    @Update
    fun updateFavoriteRecipe(recipe: RecipeDetailEntity)
}