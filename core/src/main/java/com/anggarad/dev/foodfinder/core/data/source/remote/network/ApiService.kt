package com.anggarad.dev.foodfinder.core.data.source.remote.network

import com.anggarad.dev.foodfinder.core.data.source.remote.response.*
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

    //    @Multipart
//    @POST("api/reviews")
//    suspend fun postReview(
//        @Header("Authorization") token: String,
////        @PartMap data : Map<String, RequestBody>,
//        @Part("resto_id") restoId: RequestBody,
//        @Part("user_id") userId: RequestBody,
//        @Part("rating") rating: RequestBody,
//        @Part("comments") comments: RequestBody,
//        @Part reviewImage: MultipartBody.Part?
//
//    ): PostReviewResponse
    @FormUrlEncoded
    @POST("api/reviews")
    suspend fun postReview(
        @Header("Authorization") token: String,
//        @PartMap data : Map<String, RequestBody>,
        @Field("resto_id") restoId: Int,
        @Field("user_id") userId: Int,
        @Field("rating") rating: Float,
        @Field("comments") comments: String,
        @Field("img_review_path") imgReviewPath: String

    ): PostReviewResponse

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
    ): RegisterResponse

    @GET("api/restos/search/{key}")
    suspend fun searchResto(
        @Path("key") key: String
    ): SearchResponse

    @GET("api/restos/menu/{resto_id}")
    suspend fun getRestoMenu(
        @Path("resto_id") restoId: Int
    ): MenuResponse

//    @GET("feeds/list")
//    suspend fun getRecipe(
//        @Query("start") start: Int,
//        @Query("limit") limit: Int,
//        @Query("tag") tag: String
//    ): ListRecipeResponse
}