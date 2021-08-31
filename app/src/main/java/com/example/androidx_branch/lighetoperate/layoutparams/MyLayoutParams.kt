package com.example.androidx_branch.lighetoperate.layoutparams

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * @Author Yu
 * @Date 2021/7/1 14:51
 * @Description TODO
 */
class MyLayoutParams : FrameLayout.LayoutParams {
    var from = 0
    var to = 0
    var scale = 0F
    var alpha = 0F

    constructor(c: Context, attrs: AttributeSet) : super(c, attrs)

    constructor(width: Int, height: Int) : super(width, height)

    constructor(source: ViewGroup.LayoutParams) : super(source)



}
