package com.example.androidx_branch.presents

import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.androidx_branch.presents.view.GiftSurfaceView
import kotlinx.coroutines.*
import java.util.concurrent.ArrayBlockingQueue

/**
 * @Author Yu
 * @Date 2021/6/24 10:44
 * @Description 礼物控制器
 */
class GiftShowController(var surfaceView: GiftSurfaceView) {
    var giftArrayListBlockingDeque = ArrayBlockingQueue<String>(500)

    // 初始化礼物轨道
    fun initGiftTrack(conScope: CoroutineScope) {
        // 主要是开启线程
        conScope.launch(Dispatchers.IO) {
            while (true) {
                Log.i("Zkc","来了新礼物")
                // 拿不到数据就挂起
                var content = giftArrayListBlockingDeque.take()
                withContext(Dispatchers.Main) {
                    surfaceView.drawView(content)
                }
            }
        }
    }

    // 新来了礼物
    fun addGift(content: String) {
        giftArrayListBlockingDeque.offer(content)
    }
}