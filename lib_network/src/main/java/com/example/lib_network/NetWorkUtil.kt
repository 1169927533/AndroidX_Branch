package com.example.lib_network

import retrofit2.Retrofit

/**
 * @Author Yu
 * @Date 2021/5/19 9:25
 * @Description TODO
 */
object NetWorkUtil {
    fun newRetrofit(configClass: Class<out IRetrofitConfig>): Retrofit {
        var retrofitBuilder = Retrofit.Builder()
        var config = configClass.newInstance()
        config.build(retrofitBuilder)
        return retrofitBuilder.build()
    }

}