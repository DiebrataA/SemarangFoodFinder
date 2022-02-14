package com.anggarad.dev.foodfinder.core.utils

import com.anggarad.dev.foodfinder.core.data.source.local.entity.*
import com.anggarad.dev.foodfinder.core.data.source.remote.response.*
import com.anggarad.dev.foodfinder.core.domain.model.*

object DataMapper {


    fun mapRestoEntityToDomainList(input: List<RestoEntity>): List<RestoDetail> =
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
                categories = it.categories,
                haveInternet = it.haveInternet,
                haveMeetingRoom = it.haveMeetingRoom,
                haveMusholla = it.haveMusholla,
                haveOutdoor = it.haveOutdoor,
                haveSmokingRoom = it.haveSmokingRoom,
                haveSocket = it.haveSocket,
                haveToilet = it.haveToilet
            )
        }

    fun mapRestoEntityToDomain(it: RestoEntity) =
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
            categories = it.categories,
            haveInternet = it.haveInternet,
            haveMeetingRoom = it.haveMeetingRoom,
            haveMusholla = it.haveMusholla,
            haveOutdoor = it.haveOutdoor,
            haveSmokingRoom = it.haveSmokingRoom,
            haveSocket = it.haveSocket,
            haveToilet = it.haveToilet
        )


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
        categories = input.categories,
        haveInternet = input.haveInternet,
        haveMeetingRoom = input.haveMeetingRoom,
        haveMusholla = input.haveMusholla,
        haveOutdoor = input.haveOutdoor,
        haveSmokingRoom = input.haveSmokingRoom,
        haveSocket = input.haveSocket,
        haveToilet = input.haveToilet
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
                categories = it.categories,
                haveInternet = it.haveInternet,
                haveMeetingRoom = it.haveMeetingRoom,
                haveMusholla = it.haveMusholla,
                haveOutdoor = it.haveOutdoor,
                haveSmokingRoom = it.haveSmokingRoom,
                haveSocket = it.haveSocket,
                haveToilet = it.haveToilet
            )
        }

    fun mapUserEntityToUserDetail(inputUser: UserDetailEntity) =
        UserDetail(
            userId = inputUser.userId,
            fullName = inputUser.fullName,
            address = inputUser.address,
            phoneNum = inputUser.phoneNum,
            email = inputUser.email,
            accId = inputUser.accId,
            imgProfile = inputUser.imgProfile
        )

    //    fun mapUserEntityToUser(input: UserDetailEntity) = UserDetail(
//        userId = input.userId,
//        fullName = input.fullName,
//        address = input.address,
//        phoneNum = input.phoneNum,
//        email = input.email,
//        accId = input.accId,
//        imgProfile = input.imgProfile
//    )
    fun mapUserDataLoginToEntity(input: CurrUserItem) = UserDetailEntity(
        userId = input.userId,
        fullName = input.name,
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

    fun mapMenuResponseToEntity(input: List<MenuResponseItem>): List<MenuEntity> =
        input.map {
            MenuEntity(
                restoId = it.restoId,
                menuCategory = it.menuCategory,
                menuCategoryName = it.menuCategoryName,
                menuId = it.menuId,
                menuName = it.menuName,
                menuPrice = it.menuPrice,
                isRecommended = it.isRecommended,
                description = it.description
            )
        }

    fun mapMenuEntityToDomain(input: List<MenuEntity>): List<MenuDetail> =
        input.map {
            MenuDetail(
                restoId = it.restoId,
                menuCategory = it.menuCategory,
                menuCategoryName = it.menuCategoryName,
                menuId = it.menuId,
                menuName = it.menuName,
                menuPrice = it.menuPrice,
                isRecommended = it.isRecommended,
                description = it.description
            )
        }

    fun mapSearchResponseToEntity(input: List<SearchItem>): List<SearchItemEntity> =
        input.map {
            SearchItemEntity(
                restoId = it.restoId,
                name = it.name,
                location = it.location,
                imgCover = it.imgCover
            )
        }

    fun mapSearchEntityToDomain(input: List<SearchItemEntity>): List<SearchModel> =
        input.map {
            SearchModel(
                restoId = it.restoId,
                name = it.name,
                location = it.location,
                imgCover = it.imgCover
            )
        }

}