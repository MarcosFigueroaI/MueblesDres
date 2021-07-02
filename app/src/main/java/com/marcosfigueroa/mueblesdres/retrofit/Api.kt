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

    @FormUrlEncoded
    @POST("api/addMueble.php")
    suspend fun addMueble(
        @Field("nombre") nombre: String,
        @Field("color") color: String,
        @Field("precio") precio: Int,
        @Field("descripcion") descripcion: String,
        @Field("cantidad") cantidad: Int,
    ): Response<Data>

}