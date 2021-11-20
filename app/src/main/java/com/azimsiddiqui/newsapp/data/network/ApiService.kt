package com.azimsiddiqui.newsapp.data.network

import com.azimsiddiqui.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?sources=techcrunch")
    suspend fun getNews(
        @Query("apikey") api_key:String
        //@Header("app-id") api_key:String
    ):Response<NewsResponse>

//    @GET("user/{id}")
//    suspend fun getUserDetail(
//        @Header("app-id") api_key: String,
//        @Path("id") id:String
//    ):Response<UserDetailResponse>
}