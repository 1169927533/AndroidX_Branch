package com.example.androidx_branch.lighetoperate

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.a11699.lib_util.dp
import kotlinx.android.synthetic.main.dialog_fragment_demo.view.*
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @Author Yu
 * @Date 2021/6/30 14:49
 * @Description 获取手滑动的角度
 */
class GetAngleView : View {
    var angleCallback: (angle: Float) -> Unit = {}
    private var parentWidth = 0
    private var parentHeight = 0
    private var angle = 0f
    private var behindPoint = arrayOfNulls<Float>(2) // 之前的角
    private var downPoint = arrayOfNulls<Float>(2) // 按下坐标
    private var circleCenterPoint = arrayOfNulls<Float>(2) // 圆心坐标
    private var movePoint = arrayOfNulls<Float>(2) //移动坐标
    private var circleCenterPointRadius = 5f.dp
    private var virtualCircleRadius = 0 //为了可以算我们滑动的角度，构建一个虚拟圆形 半径为宽高的最小值/2
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
        virtualCircleRadius = parentWidth.coerceAtLeast(parentHeight) / 2
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
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downPoint[0] = event.x
                downPoint[1] = event.y
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
                //  Toast.makeText(context, "角度${angle}", Toast.LENGTH_SHORT).show()
                angle = 0f
                if (isClockwise(event.x,event.y)) {
                    Toast.makeText(context, "顺时针", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "逆时针", Toast.LENGTH_SHORT).show()
                }
            }
        }
        invalidate()
        behindPoint[0] = event.x
        behindPoint[1] = event.y
        return true
    }

    private var isShouldBeGetY = false

    /**
     * 检测手指是否顺时针滑动
     * @param x 当前手指的x坐标
     * @param y 当前手指的y坐标
     * @return 是否顺时针
     */
    private fun isClockwise(x: Float, y: Float): Boolean {
        return if (abs(y - behindPoint[1]!!) - abs(x - behindPoint[0]!!) > 0) {
            x < circleCenterPoint[0]!! != y > behindPoint[1]!!
        } else {
            y < circleCenterPoint[1]!! == x >behindPoint[0]!!
        }
    }

    /**
     * 计算滑动的角度
     */
    private fun handleActionMove() {
        var linA = measureLineLength(
            movePoint[0]!!,
            circleCenterPoint[0]!!,
            movePoint[1]!!,
            circleCenterPoint[1]!!
        )
        var linB = measureLineLength(
            behindPoint[0]!!,
            circleCenterPoint[0]!!,
            behindPoint[1]!!,
            circleCenterPoint[1]!!
        )
        var lineC = 0
        var lineCx = abs(movePoint[0]!! - behindPoint[0]!!).toDouble()
            .pow(2.toDouble()).toInt()
        var lineCy = abs(movePoint[1]!! - behindPoint[1]!!).toDouble()
            .pow(2.toDouble()).toInt()
        lineC = sqrt((lineCx + lineCy).toDouble()).toInt()

        angle = fixAngle(
            Math.toDegrees(
                acos(
                    (linA.pow(2.0) + linB.pow(2.0) - lineC.toDouble().pow(2.0)) / (2 * linA * linB)
                )
            )
                .toFloat()
        )
        if (isClockwise(movePoint[0]!!, movePoint[1]!!)) {
        } else {
            angle = -angle
        }

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

    private fun measureLineLength(xA: Float, xB: Float, yA: Float, yB: Float): Double {
        return sqrt(
            abs(xA - xB).toDouble().pow(2.toDouble()) +
                    abs((yA - yB).toDouble()).pow(2.toDouble())
        )
    }
}