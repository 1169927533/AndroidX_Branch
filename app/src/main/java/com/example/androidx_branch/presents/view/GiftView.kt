package com.example.androidx_branch.presents.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.androidx_branch.R
import kotlinx.android.synthetic.main.activity_surface.view.*

/**
 * @Author Yu
 * @Date 2021/6/24 11:30
 * @Description TODO
 */
class GiftView : FrameLayout {
    var view: View? = null

    constructor(context: Context) : super(context) {
        view = LayoutInflater.from(context).inflate(R.layout.item_giftview, this,false)
        addView(view)
    }
}