package com.anggarad.dev.foodfinder.core.data.source.remote.network

import com.anggarad.dev.foodfinder.core.data.source.remote.response.*
import retrofit2.http.*

interface ApiService {

    @GET("api/restos")
    suspend fun getAllResto() : RestosResponse

    //Get Cafe lists
    @GET("api/resto_list_by_category/3")
    suspend fun getCafes() : RestosResponse

    @GET("api/users/{id}")
    suspend fun getUserDetail(
        @Path("id") userId: Int
    ) : CurrUserItem

    @FormUrlEncoded
    @POST("auth/api/v1/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("auth/api/v1/register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("phone_num") phoneNum: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : RegisterResponse

    @GET("feeds/list")
    suspend fun getRecipe(
        @Query("start") start: Int,
        @Query("limit") limit: Int,
        @Query("tag") tag: String
    ): ListRecipeResponse
}