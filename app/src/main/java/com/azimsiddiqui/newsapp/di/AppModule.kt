package com.azimsiddiqui.newsapp.di

import com.azimsiddiqui.newsapp.data.network.ApiService
import com.azimsiddiqui.newsapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//
//        private val okHttpClient = OkHttpClient()
//            .newBuilder()
//            .addInterceptor(run {
//                val httpLoggingInterceptor = HttpLoggingInterceptor()
//                httpLoggingInterceptor.apply {
//                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//                }
//            }).build()

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(OkHttpClient.Builder().also { client ->
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

    @Singleton
    @Provides
    fun getApi()= getRetrofitInstance().create<ApiService>(ApiService::class.java)

}