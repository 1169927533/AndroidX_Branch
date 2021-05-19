package com.example.androidx_branch.stock

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @Author Yu
 * @Date 2021/4/19 10:46
 * @Description 解析本地json文件
 */
class AssetsUtils {
    companion object {
        fun getJson(fileName: String, context: Context): String {
            //将json数据变成字符串
            var stringBuilder = StringBuilder()
            //获取assets资源管理器
            var assetManager = context.assets
            //通过管理器打开文件并读取
            var bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            var line: String? = ""
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            return stringBuilder.toString()
        }
    }
}