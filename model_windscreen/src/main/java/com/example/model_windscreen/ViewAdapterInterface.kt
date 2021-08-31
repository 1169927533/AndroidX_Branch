package com.example.model_windscreen

import android.graphics.Canvas
import android.view.SurfaceHolder

/**
 * @Author Yu
 * @Date 2021/8/29 8:49
 * @Description
 */
interface ViewAdapterInterface {
    fun drawView(canvas: Canvas,surfaceHolder: SurfaceHolder);
}