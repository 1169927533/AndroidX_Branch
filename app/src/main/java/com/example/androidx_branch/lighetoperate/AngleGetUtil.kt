package com.example.androidx_branch.lighetoperate

import android.os.Handler
import android.os.Message
import android.view.MotionEvent
import android.view.VelocityTracker
import android.widget.Scroller
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @Author Yu
 * @Date 2021/7/1 10:09
 * @Description 角度获取器
 */
class AngleGetUtil {
    private var angle: Float = 0f                              //旋转角度
    var angleCallback: (angle: Float) -> Unit = {}
    private var centerPoint = arrayOfNulls<Float>(2)       //旋转中心点坐标
    private var movePoint = arrayOfNulls<Float>(2)         //移动坐标
    private var behindPoint = arrayOfNulls<Float>(2)       //上一次的移动坐标 我们就是基于这个参数和movePoint来计算除旋转角度的
    private var fillScroller: Scroller? = null
    private var mVelocityTracker: VelocityTracker? = null
    private var pickTheNeedle = false                           // 是否顺势针


    constructor(
        fillScroller: Scroller,
        centerPoint: Array<Float?>,
        angleCallback: (angle: Float) -> Unit
    ) {
        this.centerPoint = centerPoint
        this.angleCallback = angleCallback
        this.fillScroller = fillScroller
        mVelocityTracker = VelocityTracker.obtain()
    }

    public fun handleEvent(event: MotionEvent) {
        mVelocityTracker!!.addMovement(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isFinish = false
                if (!fillScroller!!.isFinished()) {
                    fillScroller!!.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                movePoint[0] = event.x
                movePoint[1] = event.y
                handleActionMove()
                if (!java.lang.Float.isNaN(angle)) {
                    angleCallback.invoke(angle)
                }
            }
            MotionEvent.ACTION_UP -> {
                mVelocityTracker!!.computeCurrentVelocity(1000)
                fillScroller!!.fling(
                    0, 0,
                    mVelocityTracker!!.xVelocity.toInt(), mVelocityTracker!!.yVelocity.toInt(),
                    Int.MIN_VALUE,
                    Int.MAX_VALUE,
                    Int.MIN_VALUE,
                    Int.MAX_VALUE
                )
                startFill()
            }
        }
        behindPoint[0] = event.x
        behindPoint[1] = event.y
    }

    fun startFill() {
        GlobalScope.launch {
            computeInertialSliding()
        }
    }

    private var isShouldBeGetY = false
    private val mScrollAvailabilityRatio = 0.3f
    private var mLastScrollOffset = 0f
    private var isFinish = false

    /**
     * 处理惯性滚动
     */
    private suspend fun computeInertialSliding() {
        when {
            fillScroller!!.computeScrollOffset() -> {
                val y: Float =
                    (if (isShouldBeGetY) fillScroller!!.currY else fillScroller!!.currX) * mScrollAvailabilityRatio
                if (mLastScrollOffset != 0f) {
                    val offset = fixAngle(Math.abs(y - mLastScrollOffset))
                    if (pickTheNeedle) offset else -offset
                    angleCallback.invoke(offset)
                }
                mLastScrollOffset = y
                delay(5)
                computeInertialSliding()
            }
            fillScroller!!.isFinished -> {
                mLastScrollOffset = 0f
                isFinish = true
            }
            else -> {
                isFinish = true
            }
        }
    }


    /**
     * 计算滑动的角度
     */
    private fun handleActionMove() {
        var linA = measureLineLength(centerPoint, movePoint)
        var linB = measureLineLength(centerPoint, behindPoint)
        var lineC = measureLineLength(movePoint, behindPoint)

        angle = fixAngle(
            Math.toDegrees(
                acos(
                    (linA.pow(2.0) + linB.pow(2.0) - lineC.toDouble().pow(2.0)) / (2 * linA * linB)
                )
            )
                .toFloat()
        )
        pickTheNeedle = isClockwise(movePoint[0]!!, movePoint[1]!!)
        if (pickTheNeedle) {
        } else {
            angle = -angle
        }
    }

    // 根据两点计算两点间的长度
    private fun measureLineLength(pointA: Array<Float?>, pointB: Array<Float?>): Double {
        return sqrt(
            abs(pointA[0]!! - pointB[0]!!).toDouble().pow(2.toDouble()) +
                    abs((pointA[1]!! - pointB[1]!!).toDouble()).pow(2.toDouble())
        )
    }

    /**
     * 调整角度，使其在360之间
     *
     * @param rotation 当前角度
     * @return 调整后的角度
     */
    private fun fixAngle(rotation: Float): Float {
        var rotation = rotation
        val angle = 360f
        if (rotation < 0) {
            rotation += angle
        }
        /* if (rotation > angle) {
             rotation = rotation % angle
         }*/
        return rotation
    }

    /**
     * 检测手指是否顺时针滑动
     * @param x 当前手指的x坐标
     * @param y 当前手指的y坐标
     * @return 是否顺时针
     */
    private fun isClockwise(x: Float, y: Float): Boolean {
        isShouldBeGetY = abs(y - behindPoint[1]!!) - abs(x - behindPoint[0]!!) > 0
        return if (isShouldBeGetY) {
            x < centerPoint[0]!! != y > behindPoint[1]!!
        } else {
            y < centerPoint[1]!! == x > behindPoint[0]!!
        }
    }
}