package com.anggarad.dev.foodfinder.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anggarad.dev.foodfinder.core.data.source.local.entity.RecipeDetailEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.UserDetailEntity

@Database(
    entities = [RecipeDetailEntity::class, UserDetailEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(ListConverters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): Dao
}