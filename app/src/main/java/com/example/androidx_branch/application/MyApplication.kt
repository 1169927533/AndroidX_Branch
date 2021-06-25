package com.example.androidx_branch.application

import android.app.Application
import androidx.multidex.MultiDex
import com.example.lib_network.util.AppCache

/**
 * @Author Yu
 * @Date 2021/6/7 17:53
 * @Description TODO
 */
class MyApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        AppCache.setContext(this)
        MultiDex.install(this);
    }
}