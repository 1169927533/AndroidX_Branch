package com.example.lib_network.converterfactory;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.e("yunet", (String.format("result\nUrl=%s\nbody=%s", request.url(), responseBody.string())));
        return response;
    }

    /**
     * 三个参数 Tag就是log中的tag，msg为具体信息
     */
    public static void printLog(String tag, String msg) {
        String message = null;
        try {//需判断json是什么格式
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }
        //  String[] lines = message.split("");
        //此处也可以画分割线
        //log.e(tag,"----------------------------------------------")
        // for (String line : lines) {
        Log.d(tag, "|" + message);
        //}
        //log.e(tag,"----------------------------------------------")
    }
}