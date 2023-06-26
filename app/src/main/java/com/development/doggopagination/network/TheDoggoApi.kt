package com.development.doggopagination.network

import com.development.doggopagination.data.vo.DoggoVO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface TheDoggoApi {

    @Headers("${ApiConstants.PARAM_X_API_KEY}: ${ApiConstants.X_API_KEY}")
    @GET("v1/images/search")
    suspend fun getDoggoImages(@Query("page") page: Int, @Query("limit") size: Int): Response<List<DoggoVO>>
}