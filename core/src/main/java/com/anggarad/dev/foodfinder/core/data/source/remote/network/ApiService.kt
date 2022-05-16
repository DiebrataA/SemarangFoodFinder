package com.anggarad.dev.foodfinder.core.data.source.remote.network

import com.anggarad.dev.foodfinder.core.data.source.remote.response.*
import retrofit2.http.*

interface ApiService {

    @GET("api/resto_category")
    suspend fun getAllResto(): RestosResponse

    @GET("api/restos/{restoId}")
    suspend fun getRestoDetails(
        @Path("restoId") restoId: Int
    ): SingleRestoResponse

    //Get Cafe lists
    @GET("api/resto_list_by_category/{id}")
    suspend fun getRestoByCategory(
        @Path("id") restoId: Int
    ): RestoByCategoryResponse

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
    ): UserReviewResponse

    @FormUrlEncoded
    @POST("api/reviews")
    suspend fun postReview(
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
        @Field("name") name: String? = null,
        @Field("address") address: String? = null,
        @Field("phone_num") phoneNum: String? = null,
        @Field("email") email: String? = null,
        @Field("password") password: String? = null,
        @Field("img_profile") imgProfile: String? = null,
    ): RegisterResponse

    @GET("api/restos/search/{key}")
    suspend fun searchResto(
        @Path("key") key: String,
    ): SearchResponse

    @GET("api/menu/search/{key}")
    suspend fun searchMenu(
        @Path("key") key: String,
    ): SearchResponse

    @GET("api/restos/menu/{resto_id}")
    suspend fun getRestoMenu(
        @Path("resto_id") restoId: Int,
    ): MenuResponse

    @GET("api/categories")
    suspend fun getRestoCategories(): CategoriesResponse

    @FormUrlEncoded
    @PUT("api/users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int?,
        @Field("name") name: String?,
        @Field("address") address: String?,
        @Field("phone_num") phoneNum: String?,
        @Field("img_profile") imgProfile: String?,
    ): EditProfileResponse

    @FormUrlEncoded
    @PUT("api/reviews/{reviews_id}")
    suspend fun updateReview(
        @Path("reviews_id") id: Int?,
        @Field("rating") rating: Float?,
        @Field("comments") comments: String?,
        @Field("is_deleted") isDeleted: Int?,
    ): PostReviewResponse

    @GET("api/reviews/{reviews_id}")
    suspend fun getReviewById(
        @Path("reviews_id") id: Int?,
    ): SingleReviewResponse
}