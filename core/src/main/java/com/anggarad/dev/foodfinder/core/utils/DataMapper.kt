package com.anggarad.dev.foodfinder.core.utils

import com.anggarad.dev.foodfinder.core.data.source.local.entity.RestoEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.ReviewEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.UserDetailEntity
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RestoItems
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ReviewItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.UserResponse
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail

object DataMapper {


    fun mapRestoEntityToDomain(input: List<RestoEntity>): List<RestoDetail> =
        input.map {
            RestoDetail(
                restoId = it.restoId,
                name = it.name,
                isHalal = it.isHalal,
                opHours = it.opHours,
                contacts = it.contacts,
                address = it.address,
                imgCover = it.imgCover,
                imgMenuPath = it.imgMenuPath,
                priceRange = it.priceRange,
                location = it.location,
                isFavorite = it.isFavorite,
                ratingAvg = it.ratingAvg,
                categories = it.categories
            )
        }

    fun mapRestoDomainToEntity(input: RestoDetail) = RestoEntity(
        restoId = input.restoId,
        name = input.name,
        isHalal = input.isHalal,
        opHours = input.opHours,
        contacts = input.contacts,
        address = input.address,
        imgCover = input.imgCover,
        imgMenuPath = input.imgMenuPath,
        priceRange = input.priceRange,
        location = input.location,
        isFavorite = input.isFavorite,
        ratingAvg = input.ratingAvg,
        categories = input.categories
    )

    fun mapRestoResponsetoEntity(input: List<RestoItems>): List<RestoEntity> =
        input.map {

            RestoEntity(
                restoId = it.restoId,
                name = it.name,
                isHalal = it.isHalal,
                contacts = it.contacts,
                opHours = it.opHours,
                address = it.address,
                imgCover = it.imgCover,
                imgMenuPath = it.imgMenuPath,
                location = it.location,
                priceRange = it.priceRange,
                isFavorite = false,
                ratingAvg = it.ratingAvg,
                categories = it.categories
            )
        }

    fun mapUserEntityToUser(input: UserDetailEntity) = UserDetail(
        userId = input.userId,
        fullName = input.fullName,
        address = input.address,
        phoneNum = input.phoneNum,
        email = input.email,
        accId = input.accId,
        imgProfile = input.imgProfile
    )

    fun mapUserResponseToEntity(input: UserResponse) = UserDetailEntity(
        userId = input.response.userId,
        fullName = input.response.name,
        address = input.response.address,
        phoneNum = input.response.phoneNum,
        email = input.response.email,
        accId = input.response.accId,
        imgProfile = input.response.imgProfile

    )

    fun mapReviewResponseTOEntity(input: List<ReviewItem>): List<ReviewEntity> =
        input.map {
            ReviewEntity(
                restoId = it.restoId,
                reviewsId = it.reviewsId,
                date = it.date,
                comments = it.comments,
                userId = it.userId,
                rating = it.rating,
                name = it.name,
                imgReviewPath = it.imgReviewPath,
                imgProfile = it.imgProfile

            )
        }

    fun mapReviewEntityToDomain(input: List<ReviewEntity>): List<ReviewDetails> =
        input.map {
            ReviewDetails(
                reviewsId = it.reviewsId,
                restoId = it.restoId,
                userId = it.userId,
                date = it.date,
                comments = it.comments,
                rating = it.rating,
                name = it.name,
                imgReviewPath = it.imgReviewPath,
                imgProfile = it.imgProfile
            )
        }
}