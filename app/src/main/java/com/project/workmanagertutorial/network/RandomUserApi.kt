package com.project.workmanagertutorial.network

import com.project.workmanagertutorial.model.User
import retrofit2.Response
import retrofit2.http.GET

interface RandomUserApi {

    @GET("api/")
    suspend fun fetchUsersResponse(): Response<User>
}