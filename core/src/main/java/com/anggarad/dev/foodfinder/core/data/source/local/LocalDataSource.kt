package com.anggarad.dev.foodfinder.core.data.source.local

import com.anggarad.dev.foodfinder.core.data.source.local.entity.RecipeDetailEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.UserDetailEntity
import com.anggarad.dev.foodfinder.core.data.source.local.room.Dao
import com.anggarad.dev.foodfinder.core.data.source.remote.response.CurrUserItem
import kotlinx.coroutines.flow.Flow


class LocalDataSource(private val dao: Dao) {

    fun getUserData(userId: Int): Flow<UserDetailEntity> = dao.getUser(userId)

    suspend fun insertUserData(user: UserDetailEntity) = dao.insertUser(user)

    fun getAllRecipe(): Flow<List<RecipeDetailEntity>> = dao.getAllRecipe()

    fun getFavoriteRecipe(): Flow<List<RecipeDetailEntity>> = dao.getFavoriteRecipe()

    suspend fun insertRecipe(recipeList: List<RecipeDetailEntity>) =
        dao.insertRecipe(recipeList)

    fun setFavoriteRecipe(recipe: RecipeDetailEntity, newState: Boolean) {
        recipe.isFavorite = newState
        dao.updateFavoriteRecipe(recipe)
    }
}