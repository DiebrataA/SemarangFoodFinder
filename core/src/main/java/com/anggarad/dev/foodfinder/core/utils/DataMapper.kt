package com.anggarad.dev.foodfinder.core.utils

import com.anggarad.dev.foodfinder.core.data.source.local.entity.RestoEntity
import com.anggarad.dev.foodfinder.core.data.source.local.entity.UserDetailEntity
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ResponseItem
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
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
                isFavorite = it.isFavorite
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
        isFavorite = input.isFavorite
    )

    fun mapRestoResponsetoEntity(input: List<ResponseItem>): List<RestoEntity> =
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
                isFavorite = false
            )
        }

    fun mapUserEntityToUser(input: UserDetailEntity) = UserDetail(
        userId = input.userId,
        fullName = input.fullName,
        address = input.address,
        phoneNum = input.phoneNum,
        email = input.email,
        token = input.token
    )

    fun mapUserResponseToEntity(input: LoginResponse) = UserDetailEntity(
        userId = input.currUser.userId,
        fullName = input.currUser.name,
        address = input.currUser.address,
        phoneNum = input.currUser.phoneNum,
        email = input.currUser.email,
        token = input.token

    )
}