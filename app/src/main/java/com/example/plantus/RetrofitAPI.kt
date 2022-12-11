package com.example.plantus

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("gardenList")
    fun getPlantList(
        @Query("serviceKey" ,encoded = true) serviceKey: String,
        @Query("sText") sText: String
    ): Call<PlantList>
}

class PlantList {
    @Xml(name="response")
    data class Response(
        @Element
        val header: retrofit2.http.Header,
        @Element
        val body: Body,
    )
    @Xml(name = "header")
    data class Header(
        @PropertyElement
        val resultCode: Int,
        @PropertyElement
        val resultMsg: String,
    )
    @Xml(name= "items")
    data class Items(
        @Element(name="item")
        val item: List<Item>
    )
    @Xml
    data class Item(
        // xml 데이터 받아서 name과 파라미터명이 일치하는지 확인
        @PropertyElement(name = "cntntsNo") var plantNumber: String?,
        @PropertyElement(name = "cntntsSj") var plantName: String?,
    )
}
