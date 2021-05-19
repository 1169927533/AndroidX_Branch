package com.example.androidx_branch.process

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.androidx_branch.R

/**
 * @Author Yu
 * @Date 2021/3/11 15:02
 * @Description 自定义进度条
 */
class CustomProcessBarView : View {
    var processBarWidth = 0//进度条长度
    var processBarHeight = 0//进度条宽度
    var bgColor = Color.BLUE
    var processBarColor = Color.BLACK
    var processBarRadius: Float = 30f//圆角半径
    var currentProcessValue = 0//当前的进度值

    val paintBg by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND//圆角
            isAntiAlias = true//线条更加平顺
            color = resources.getColor(R.color.color_000000)
            isDither = true
        }
    }
    val paintProcess by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND//圆角
            isAntiAlias = true//线条更加平顺
            color = processBarColor
            isDither = true
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.processBarView)
        currentProcessValue = typeArray.getInteger(R.styleable.processBarView_processvalue, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        processBarHeight = MeasureSpec.getSize(heightMeasureSpec)
        processBarWidth = MeasureSpec.getSize(widthMeasureSpec)
        processBarRadius = processBarHeight / 2f
        paintBg.strokeWidth = processBarRadius * 2
        paintProcess.strokeWidth = processBarRadius * 2

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val linearGradient =
            LinearGradient(
                0f,
                0f,
                processBarWidth.toFloat(),
                0f,
                resources.getColor(R.color.color_FFC9A0),
                resources.getColor(R.color.color_FFEDDE),
                Shader.TileMode.CLAMP
            )
        paintProcess.shader = linearGradient
        canvas?.drawLine(
            processBarRadius,
            processBarRadius,
            processBarWidth.toFloat() - processBarRadius,
            processBarRadius,
            paintBg
        )
        var stopY = 0f
        if(currentProcessValue.toFloat() - processBarRadius<processBarRadius){
            stopY = processBarRadius
        }else{
            stopY = currentProcessValue.toFloat() - processBarRadius
        }
        canvas?.drawLine(
            processBarRadius - 1f,
            processBarRadius,
            stopY,
            processBarRadius,
            paintProcess
        )


    }
}