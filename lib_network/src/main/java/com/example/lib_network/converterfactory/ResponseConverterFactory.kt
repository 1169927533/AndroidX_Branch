package com.example.lib_network.converterfactory

import android.util.Log
import com.example.lib_network.bean.HttpResponseMsg
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * @Author Yu
 * @Date 2021/5/19 10:29
 * @Description 网络请求结果解析器
 */
class ResponseConverterFactory<T> : Converter<ResponseBody, T> {
    lateinit var gson: Gson
    lateinit var type: Type
    lateinit var annotations: Array<Annotation>

    constructor(gson: Gson, type: Type, annotations: Array<Annotation>) {
        this.gson = gson
        this.type = type
        this.annotations = annotations
    }

    override fun convert(value: ResponseBody): T? {
        val response = value.string()
        Log.i("yuNet", "请求结果json：${response}")
        val baseBean = gson.fromJson<HttpResponseMsg<T>>(
            response,
            object : TypeToken<HttpResponseMsg<T>>() {}.type
        )
        return baseBean.data
    }

}