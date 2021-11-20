package com.azimsiddiqui.newsapp.data.repository

import com.azimsiddiqui.newsapp.data.network.ApiService
import com.azimsiddiqui.newsapp.util.Constants
import javax.inject.Inject

class NewsRepository @Inject constructor(val apiService: ApiService) {
    suspend fun getAllNews()=apiService.getNews(Constants.API_KEY)
}