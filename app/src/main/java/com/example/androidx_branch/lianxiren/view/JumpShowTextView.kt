package com.example.androidx_branch.lianxiren.view

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by xuekai on 2017/8/22.
 */
class JumpShowTextView : FrameLayout {


    val allTime = 700

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
    }

    var placeHolder: PercentTextView? = null

    var realTextView: PercentTextView? = null
    var withAnimation = true

    var text: String? = ""
        set(value) {
            reCreateObject()
            init()
            field = value
            placeHolder?.setText(value)
            start()
        }

    fun reCreateObject() {
        placeHolder = PercentTextView(context)
        placeHolder?.textSize = textSize
        placeHolder?.layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        placeHolder?.visibility = View.INVISIBLE

        realTextView = PercentTextView(context)
        realTextView?.setTextColor(color)
        realTextView?.textSize = textSize
        realTextView?.paint?.setFakeBoldText(isBold)
        realTextView?.layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        if (singline) {
            placeHolder?.setSingleLine(singline)
            placeHolder?.ellipsize = TextUtils.TruncateAt.END
            realTextView?.ellipsize = TextUtils.TruncateAt.END
            realTextView?.setSingleLine(singline)
        }

    }

    private fun init() {
//        setBackgroundColor(0xff00ff00.toInt())
        removeAllViews()
        addView(placeHolder)
        addView(realTextView)
    }


    var content: String? = ""

    var isBold: Boolean = false
    var color: Int = Color.BLACK
    var singline = false

    var textSize: Float = 52F

    //线程正在运行
    var isRun: Boolean = false

    var marginBottom: Float = 0f

    fun start() {
        if (withAnimation) {
            content = ""
            if(isRun){
                return
            }
            isRun = true
            var index = 0
            text?.let {
                val intervalTime = allTime / it.length
                GlobalScope.launch(Dispatchers.Main) {
                    while (index < it.length) {
                        delay(intervalTime.toLong())
                        content = content + it[index]
                        index++
                        realTextView?.setText(content)
                    }
                }
            }

        } else {
            realTextView?.setText(text)
        }
    }

}