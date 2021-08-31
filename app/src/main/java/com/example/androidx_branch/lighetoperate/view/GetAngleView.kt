package com.example.androidx_branch.lighetoperate.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.a11699.lib_util.dp
import com.example.androidx_branch.lighetoperate.helper.AngleGetUtil

/**
 * @Author Yu
 * @Date 2021/6/30 14:49
 * @Description 获取手滑动的角度
 */
class GetAngleView : View {
    private var parentWidth = 0
    private var parentHeight = 0
    private var behindPoint = arrayOfNulls<Float>(2) // 之前的角
    private var downPoint = arrayOfNulls<Float>(2) // 按下坐标
    private var circleCenterPoint = arrayOfNulls<Float>(2) // 圆心坐标
    private var movePoint = arrayOfNulls<Float>(2) //移动坐标
    private var circleCenterPointRadius = 5f.dp
    private var angleGetUtil: AngleGetUtil? = null
    private val linePaint by lazy {
        Paint().apply {
            color = Color.WHITE
            strokeWidth = 3f;
            style = Paint.Style.STROKE
            pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)
        }
    }

    private val centerCirclePaint by lazy {
        Paint().apply {
            color = Color.GREEN
            style = Paint.Style.FILL
        }
    }

    private val downCirclePaint by lazy {
        Paint().apply {
            color = Color.BLUE
            style = Paint.Style.FILL
        }
    }
    private val moveCirclePaint by lazy {
        Paint().apply {
            color = Color.YELLOW
            style = Paint.Style.FILL
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        behindPoint[0] = 0f
        behindPoint[1] = 0f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        circleCenterPoint[0] = parentWidth / 2f
        circleCenterPoint[1] = parentHeight / 2f
    }

    fun initParams(angleGetUtil: AngleGetUtil) {
        this.angleGetUtil = angleGetUtil
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { mCanvas ->
            mCanvas.drawColor(Color.BLACK)
            mCanvas.drawLine(
                0f,
                parentHeight / 2f,
                parentWidth.toFloat(),
                parentHeight / 2f,
                linePaint
            )
            mCanvas.drawLine(
                parentWidth / 2f,
                0f,
                parentWidth / 2f,
                parentHeight.toFloat(),
                linePaint
            )
            // 画圆心
            mCanvas.drawCircle(
                circleCenterPoint[0]!!,
                circleCenterPoint[1]!!,
                circleCenterPointRadius,
                centerCirclePaint
            )
            // 画按下的园
            if (downPoint[0] != null && downPoint[0] != 0f) {
                mCanvas.drawCircle(
                    downPoint[0]!!,
                    downPoint[1]!!,
                    circleCenterPointRadius,
                    downCirclePaint
                )
            }
            if (movePoint[0] != null && movePoint[0] != 0f) {
                mCanvas.drawCircle(
                    movePoint[0]!!,
                    movePoint[1]!!,
                    circleCenterPointRadius,
                    moveCirclePaint
                )
                var path = Path()
                path.moveTo(circleCenterPoint[0]!!, circleCenterPoint[1]!!)
                path.lineTo(downPoint[0]!!, downPoint[1]!!)
                path.lineTo(movePoint[0]!!, movePoint[1]!!)
                path.close()
                mCanvas.drawPath(path, linePaint)
            }

        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        angleGetUtil!!.handleEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downPoint[0] = event.x
                downPoint[1] = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                movePoint[0] = event.x
                movePoint[1] = event.y
            }
        }
        invalidate()
        return true
    }
}