package com.anggarad.dev.foodfinder.core.data.source.remote.network

import com.anggarad.dev.foodfinder.core.data.source.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("api/resto_category")
    suspend fun getAllResto(): RestosResponse

    //Get Cafe lists
    @GET("api/resto_list_by_category/3")
    suspend fun getCafes(): RestosResponse

    @GET("api/users/{id}")
    suspend fun getUserDetail(
        @Path("id") userId: Int
    ): UserResponse

    @GET("api/reviews/resto_reviews/{id}")
    suspend fun getRestoReviews(
        @Path("id") restoId: Int
    ): ReviewResponse

    @GET("api/reviews/user_reviews/{id}")
    suspend fun getUserReviews(
        @Path("id") userId: Int
    ): ReviewResponse

    @Multipart
    @POST("api/reviews")
    suspend fun postReview(
        @Part("resto_id") restoId: RequestBody,
        @Part("user_id") userId: RequestBody,
        @Part("rating") rating: RequestBody,
        @Part("comments") comments: RequestBody,
        @Part reviewImage: MultipartBody.Part
    ): ReviewResponse

    @FormUrlEncoded
    @POST("auth/api/v1/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

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