package com.example.androidx_branch.spann

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @Author Yu
 * @Date 2021/7/2 16:36
 * @Description TODO
 */
class MoveViewGroup : ConstraintLayout {
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