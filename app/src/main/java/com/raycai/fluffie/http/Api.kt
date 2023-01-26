package com.raycai.fluffie.http

import com.dam.bestexpensetracker.data.constant.Const.Companion.API_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class Api {

    private fun okClient(): OkHttpClient? {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    fun ApiClient(): ApiInterface {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Creating object for our interface
        var api: ApiInterface = retrofit.create(ApiInterface::class.java)
        return api
    }
}