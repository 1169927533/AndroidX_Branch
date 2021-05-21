package com.example.lib_network.retrofitBuild

import android.util.Log
import com.example.lib_network.IRetrofitConfig
import com.example.lib_network.converterfactory.CustomGsonConverterFactory
import com.example.lib_network.converterfactory.CustomLogInterceptor
import com.example.lib_network.converterfactory.ResponseConverterFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.log


/**
 * @Author Yu
 * @Date 2021/5/19 9:40
 * @Description TODO
 */
open class AppHttpConfig : IRetrofitConfig {
    override fun build(builder: Retrofit.Builder?) {
        builder!!.client(client())
        builder.baseUrl("https://wanandroid.com/")
        builder.addConverterFactory(CustomGsonConverterFactory())
        builder.addConverterFactory(GsonConverterFactory.create(Gson()))
        //builder.addCallAdapterFactory(ThreadCallAdapterFactory)  rxjava适配器
    }

    override fun reset(builder: Retrofit.Builder?) {

    }

    override fun client(): OkHttpClient {
        var okHttpClientBuilder = OkHttpClient.Builder()
        //  okHttpClientBuilder.addInterceptor(MainInterceptor())
        okHttpClientBuilder.addNetworkInterceptor(CustomLogInterceptor())
        clientToOut(okHttpClientBuilder)
        // okHttpClientBuilder.addInterceptor(CacheInterceptor())
        // okHttpClientBuilder.cache(HttpCache.getCache(cachePath()))
        okHttpClientBuilder.retryOnConnectionFailure(true)
        okHttpClientBuilder.connectTimeout(5000, TimeUnit.MILLISECONDS)
        //日志显示级别。
        val level = HttpLoggingInterceptor.Level.BODY
        //http拦截器。
        var loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger() {
            Log.e("yunet", it)
        });
        loggingInterceptor.level = level;
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder.build()
    }

    fun clientToOut(clientBuilder: OkHttpClient.Builder?) {

    }
}