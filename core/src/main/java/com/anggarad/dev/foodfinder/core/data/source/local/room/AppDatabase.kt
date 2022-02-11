package com.anggarad.dev.foodfinder.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anggarad.dev.foodfinder.core.data.source.local.entity.*

@Database(
    entities = [UserDetailEntity::class, RestoEntity::class, ReviewEntity::class, MenuEntity::class, SearchItemEntity::class],
    version = 1, exportSchema = false
)
@TypeConverters(ListConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): Dao
}