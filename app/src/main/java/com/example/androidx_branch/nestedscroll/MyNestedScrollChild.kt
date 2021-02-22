package com.example.androidx_branch.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import com.uppack.lksmall.baseyu.Utils
import com.uppack.lksmall.baseyu.weight.util.ViewUtil


/**
 * @Author Yu
 * @Date 2021/2/22 14:01
 * @Description TODO
 */
class MyNestedScrollChild : LinearLayout, NestedScrollingChild {
    private var lastY = 0
    private var showHeight = 0
    private var offset = IntArray(2) //偏移量
    private var consumed = IntArray(2) //消费
    val mNestedScrollingChildHelper: NestedScrollingChildHelper by lazy {
        NestedScrollingChildHelper(this).apply {
            isNestedScrollingEnabled = true

        }
    }

    constructor(context: Context, attributeSet: AttributeSet):super(context,attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        showHeight = measuredHeight
        var heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_DOWN -> lastY = event.rawY.toInt()
            MotionEvent.ACTION_MOVE -> {
                val y = event.rawY.toInt()
                val dy = y - lastY
                lastY = y
                if (startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL)
                        && dispatchNestedPreScroll(0, dy, consumed, offset)) //如果找到了支持嵌套滑动的父类,父类进行了一系列的滑动
                {
                    //获取滑动距离
                    val remain = dy - consumed[1]
                    if (remain != 0) {
                        scrollBy(0, -remain)
                    }
                } else {
                    scrollBy(0, -dy)
                }
            }
        }
        return true
    }

    //限制滚动范围
    override fun scrollTo(x: Int, y: Int) {
        var y = y
        val maxY = measuredHeight - showHeight
        if (y > maxY) {
            y = maxY
        }
        if (y < 0) {
            y = 0
        }
        super.scrollTo(x, y)
    }

    //实现一下接口
    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mNestedScrollingChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return mNestedScrollingChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mNestedScrollingChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }
}