package com.example.androidx_branch.spann

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

/**
 * @Author Yu
 * @Date 2021/7/2 15:31
 * @Description TODO
 */
class MoveImgView : AppCompatImageView {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    var mDownX = 0f
    var mDownY = 0f
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mDownX = event.x
                    mDownY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    offsetTopAndBottom((event.y - mDownY).toInt())
                    offsetLeftAndRight((event.x - mDownX).toInt())
                }
                MotionEvent.ACTION_UP -> {

                }
            }

        }
        return true
    }

}