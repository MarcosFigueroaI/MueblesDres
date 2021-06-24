package com.marcosfigueroa.mueblesdres.retrofit

import com.marcosfigueroa.mueblesdres.model.Data
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("api.php")
    suspend fun getMuebles(): Response<Data>

}