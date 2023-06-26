package com.development.doggopagination.data.model

import com.development.doggopagination.data.vo.DoggoVO
import retrofit2.Response

interface TheDoggoModel {
     suspend fun getDoggoList(page: Int, size: Int) : Response<List<DoggoVO>>
}