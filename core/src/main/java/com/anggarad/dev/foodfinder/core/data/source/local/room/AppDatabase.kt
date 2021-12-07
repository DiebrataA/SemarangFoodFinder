package com.anggarad.dev.foodfinder.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anggarad.dev.foodfinder.core.data.source.local.entity.RestoEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.ReviewEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.UserDetailEntity

@Database(
    entities = [UserDetailEntity::class, RestoEntity::class, ReviewEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(ListConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): Dao
}