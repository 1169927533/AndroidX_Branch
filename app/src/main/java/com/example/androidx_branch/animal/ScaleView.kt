package com.example.androidx_branch.animal

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.uppack.lksmall.baseyu.weight.util.ViewUtil

/**
 * @Author Yu9s
 * @Date 2021/3/18 9:34
 * @Description 在给定的距离内实现对view的缩放。
 * 公式 =
 *  (scaleDistance - 目标需要移动的距离) / (scaleDistance)  =  (目标的宽度) / （原本的宽度）
 *  通过这个公式求出： scaleDistance
 */

class ScaleView : androidx.appcompat.widget.AppCompatImageView {
    var targetViewXLocation = ViewUtil.dip2px(200f) //指定view距离这个view的距离
    var targetViewWidth = ViewUtil.dip2px(20f)

    var scaleDistance = 0f// ViewUtil.dip2px(250f)//这个值是根据指定距离动态算出来的
    var viewWidth = 0
    var viewHeight = 0
    var downX = 0f

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        scaleDistance =
            (targetViewXLocation * viewWidth).toFloat() / (viewWidth - targetViewWidth).toFloat()

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.rawX
            }
            MotionEvent.ACTION_MOVE -> {
                var movx = event.rawX
                var moveDistance = movx - downX
                var scale = (scaleDistance - moveDistance) / (scaleDistance).toFloat()
                this.pivotX = 1.0f
                this.pivotY = 0.0f
                this.scaleX = scale
                this.scaleY = scale
                this.x = moveDistance
                Log.i("zjc","width = ${this.measuredWidth}  ${this.width} ${this.x}  ")
            }
        }
        return true
    }

    fun animal() {
        var scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.2f)
        var scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.2f)
        var tranX = ObjectAnimator.ofFloat(this, "translationX", 0f, scaleDistance.toFloat())
        var animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, tranX)
        animatorSet.start()
    }
}