package com.example.androidx_branch.lianxiren

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.weight.util.ViewUtil

/**
 * @Author Yu
 * @Date 2021/2/23 15:43
 * @Description 画头部
 */
class TelItemDirection(context: Context) : RecyclerView.ItemDecoration() {
    var groupHeaderHeight = 40 * ViewUtil.getDisplayMetrics()!!.density
    val headPaint by lazy {
        Paint().apply {
            color = context.resources.getColor(R.color.color_D8D8D8)
        }
    }
    val textPaint by lazy {
        Paint().apply {
            color = context.resources.getColor(R.color.color_A8ADBF)
            textSize = 16f * 3
        }
    }

    val textRect by lazy {
        Rect()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.adapter is TelAdapter) {
            val adapter: TelAdapter? = parent.adapter as TelAdapter?
            // 当前屏幕的item个数
            val count = parent.childCount
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            for (i in 0 until count) {
                // 获取对应i的View
                val view = parent.getChildAt(i)
                // 获取View的布局位置
                val position = parent.getChildLayoutPosition(view)
                // 是否是头部
                val isGroupHeader: Boolean = adapter!!.isGroupHeader(position)
                if (isGroupHeader && view.top - groupHeaderHeight - parent.paddingTop >= 0) {
                    c.drawRect(
                        left.toFloat(),
                        (view.top - groupHeaderHeight).toFloat(),
                        right.toFloat(),
                        view.top.toFloat(),
                        headPaint
                    )
                    val groupName: String = adapter.getGroupName(position)
                    textPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                    c.drawText(
                        groupName, (left + 15*ViewUtil.getDisplayMetrics()!!.density).toFloat(), (view.top -
                                groupHeaderHeight / 2 + textRect.height() / 2).toFloat(), textPaint
                    )
                } else if (view.top - groupHeaderHeight - parent.paddingTop >= 0) {
                    // 分割线
                    c.drawRect(
                        left.toFloat(),
                        (view.top - 4).toFloat(),
                        right.toFloat(),
                        view.top.toFloat(), headPaint
                    )
                }
            }
        }
    }

    //在recycleview绘制完后在绘制
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, 1)
        if (parent.adapter is TelAdapter) {
            val adapter: TelAdapter? = parent.adapter as TelAdapter?
            val position = parent.getChildLayoutPosition(view)
            val isGroupHeader: Boolean = adapter!!.isGroupHeader(position)
            if (isGroupHeader) {
                // 如果是头部，预留更大的地方
                outRect[0, groupHeaderHeight.toInt(), 0] = 0
            }
        }
    }
}