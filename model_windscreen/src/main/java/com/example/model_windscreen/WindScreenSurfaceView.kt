package com.example.model_windscreen

import android.animation.Animator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*

/**
 * @Author Yu
 * @Date 2021/8/29 8:26
 * @Description TODO
 */
class WindScreenSurfaceView : SurfaceView, SurfaceHolder.Callback2 {
    private var paint: Paint? = null

    private var windData = ArrayList<WindScreenBean>()
    private var mHolder: SurfaceHolder? = null
    private var mCanvas: Canvas? = null

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mHolder = holder
        mHolder?.addCallback(this)
        paint = Paint().apply {
            color = Color.BLACK
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        windData.add(WindScreenBean(0f, "one"))
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        openSonThreadToDraw()

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mHolder?.removeCallback(this)
    }

    override fun surfaceRedrawNeeded(holder: SurfaceHolder) {

    }

    var shouldRun = true
    var currentValue: Float = 0f

    // 开启子线程，无限播放
    private fun openSonThreadToDraw() {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                delay(2000)
                windData.add(WindScreenBean(0f, "one"))
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            while (shouldRun) {

                drawContent()

            }
        }
    }

    suspend private fun drawContent() {
        mCanvas = holder.lockCanvas()
        mCanvas?.drawColor(context.resources.getColor(R.color.white))
        for (index in 0 until windData.size) {
            var value = windData[index]
            if (value.objectAnimator?.isRunning == false) {
                withContext(Dispatchers.Main) {
                    value.objectAnimator?.start()
                    value.objectAnimator?.removeAllListeners()
                    value.objectAnimator?.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            windData.remove(value)
                        }

                        override fun onAnimationCancel(animation: Animator?) {
                        }

                        override fun onAnimationRepeat(animation: Animator?) {
                        }
                    })
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mCanvas?.drawRoundRect(
                    value.translationX,
                    0f,
                    value.translationX + 100f,
                    100f,
                    0f,
                    0f,
                    paint!!
                )
            }
            Log.i("zjc", "${Thread.currentThread().name}")
        }
        holder.unlockCanvasAndPost(mCanvas)
    }
}