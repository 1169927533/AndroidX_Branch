package com.example.androidx_branch.spann

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @Author Yu
 * @Date 2021/7/2 16:56
 * @Description TODO
 */
class MyAreaViewPager : ViewPager {
    var moveViewGroup: MoveImgView? = null

    fun setTargetView(moveViewGroup: MoveImgView) {
        this.moveViewGroup = moveViewGroup
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    var mDownX = 0f
    var mDownY = 0f


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    moveViewGroup?.let {
                        mDownX = event.rawX
                        mDownY = event.rawY
                        if ((mDownX >= it.x && mDownX <= it.x + it.width) && (mDownY >= it.y && mDownY <= it.y + it.height)) {
                            return false
                        }
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    moveViewGroup?.let {
                        mDownX = event.rawX
                        mDownY = event.rawY
                        if ((mDownX >= it.x && mDownX <= it.x + it.width) && (mDownY >= it.y && mDownY <= it.y + it.height)) {
                            return false
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {

                }
            }
            return super.onTouchEvent(ev)
        }
        return super.onTouchEvent(ev)
    }
}