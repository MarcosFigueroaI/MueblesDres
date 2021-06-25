package com.marcosfigueroa.mueblesdres.retrofit

import com.marcosfigueroa.mueblesdres.model.Data
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("api.php")
    suspend fun getMuebles(): Response<Data>

    @FormUrlEncoded
    @POST("api/login.php")
    suspend fun login(
        @Field("usuario") usuario: String,
        @Field("password") password: String
    ): Response<Data>

}