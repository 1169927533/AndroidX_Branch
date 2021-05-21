package com.example.lib_network.converterfactory;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author superman
 * 网络请求日志拦截器
 */
public class CustomLogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request request = chain.request();
        final Response response = chain.proceed(request);
        final ResponseBody body = response.body();
        final String bodyString = body.string();
        Log.e("yunet", (String.format("result\nUrl=%s\nbody=%s", request.url(), bodyString)));

        return response.newBuilder()
                .headers(response.headers())
                .body(ResponseBody.create(body.contentType(), bodyString))
                .build();
    }
}