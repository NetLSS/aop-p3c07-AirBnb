package com.lilcode.aop.p3c07.airbnb

import retrofit2.Call
import retrofit2.http.GET

interface HouseService {
    @GET("/v3/df8177fd-7299-407d-9c56-e2b9072ea50f")
    fun getHouseList(): Call<HouseDto>
}