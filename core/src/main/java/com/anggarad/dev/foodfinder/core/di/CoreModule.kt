package com.anggarad.dev.foodfinder.core.di

import androidx.room.Room
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.repository.*
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.room.AppDatabase
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiService
import com.anggarad.dev.foodfinder.core.domain.repository.*
import com.anggarad.dev.foodfinder.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AppDatabase>().recipeDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("diBsDev".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "FoodFinder.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val postReviewModule = module {
    single {
        val token: String? = null
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader(
                        "token",
                        "$token"
                    )
                }.build())
            }
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(loggingInterceptor)
                }
            }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.4:4000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { DataStoreManager(get()) }
    factory { AppExecutors() }

    single<IAuthRepository> {
        AuthRepository(
            get(),
            get(),
        )
    }

    single<IUserRepository> {
        UserRepository(
            get(),
            get(),
            get()
        )
    }

    single<IRestoRepository> {
        RestoRepository(
            get(),
            get(),
            get()
        )
    }

    single<IMainRepository> {
        MainRepository(get())
    }

    single<IReviewRepository> {
        ReviewRepository(
            get(),
            get()
        )
    }
}