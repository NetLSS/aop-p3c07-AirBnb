package com.lilcode.aop.p3c07.airbnb.retrofit

import com.lilcode.aop.p3c07.airbnb.retrofit.HouseDto
import retrofit2.Call
import retrofit2.http.GET

// for retrofit
interface HouseService {
    @GET("/v3/6c14ab02-b757-4931-b3ba-2dd9e5765073") // 생성해둔 mocky 주소
    fun getHouseList(): Call<HouseDto>
}