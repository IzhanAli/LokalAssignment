package com.lokalassignment.applokal.util

import com.lokalassignment.applokal.models.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobsAPI {

    @GET("common/jobs")
    suspend fun getListings(
        @Query("page")
        page: Int = 1
    ): Response<APIResponse>
}