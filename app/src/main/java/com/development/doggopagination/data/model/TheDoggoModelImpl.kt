package com.development.doggopagination.data.model

import com.development.doggopagination.data.vo.DoggoVO
import com.development.doggopagination.network.TheDoggoClient
import retrofit2.Response

class TheDoggoModelImpl : TheDoggoModel {
    private val mTheDoggoClient = TheDoggoClient()

    override suspend fun getDoggoList(page: Int, size: Int): Response<List<DoggoVO>> {
        return mTheDoggoClient.mTheDoggoApi.getDoggoImages(page = page, size = size)
    }
}