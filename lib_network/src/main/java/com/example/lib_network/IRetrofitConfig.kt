package com.example.lib_network

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @Author Yu
 * @Date 2021/5/19 9:35
 * @Description TODO
 */
interface IRetrofitConfig {
    fun build(builder: Retrofit.Builder?)

    fun reset(builder: Retrofit.Builder?)

    fun client(): OkHttpClient
}