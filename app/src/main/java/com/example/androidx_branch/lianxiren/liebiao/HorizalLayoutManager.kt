package com.example.androidx_branch.lianxiren.liebiao

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author Yu
 * @Date 2021/2/23 17:51
 * @Description TODO
 */
class HorizalLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        /**
         * 第一步回收复用
         */
        detachAndScrapAttachedViews(recycler)
        layOutFirst(recycler, state)
    }

    //初始化布局 在第一布局的时候 只布局显示在界面上的view
    private fun layOutFirst(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount > 0) {
            var i = 0
            while (true) {
                var childView = recycler.getViewForPosition(i)
                addView(childView)
                measureChildWithMargins(childView, 0, 0)
                var left = i * childView.measuredWidth / 2
                var top = 0
                var right = left + childView.measuredWidth
                var bottom = height
                layoutDecoratedWithMargins(childView, left, top, right, bottom)
                if (childView.right > width) {
                    break
                }
                i++
            }
        }
    }

    //滚动布局的时候 动态添加view
    private fun layoutViewOnScroll(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ) {
        //填充尾部
        var anchorView = getChildAt(childCount - 1)
        val anchorPosition = getPosition(anchorView!!)
        if (dx > 0) {
            while(anchorView!!.right < width){
                var position = (anchorPosition + 1) % itemCount
                if (position < 0) position += itemCount
                //在尾部addView
                var view = recycler.getViewForPosition(position) //这里可能有问题 越界
                addView(view)
                measureChildWithMargins(view, 0, 0)
                var left = anchorView.right-view.measuredWidth/2
                var top = 0
                var right = left + view.measuredWidth
                var bottom = height
                layoutDecoratedWithMargins(view, left, top, right, bottom)
                anchorView = view
            }
        } else {
            //填充首部
            var anchorView = getChildAt(0)
            val anchorPosition = getPosition(anchorView!!)
            while (anchorView!!.left > paddingLeft) {
                var position = (anchorPosition - 1) % itemCount
                if (position < 0) position += itemCount
                val scrapItem = recycler.getViewForPosition(position)
                addView(scrapItem, 0)
                measureChildWithMargins(scrapItem, 0, 0)
                val right = anchorView.left
                val top = paddingTop
                val left = right - getDecoratedMeasuredWidth(scrapItem)
                val bottom = top + getDecoratedMeasuredHeight(scrapItem)
                layoutDecorated(
                    scrapItem, left, top,
                    right, bottom
                )
                anchorView = scrapItem
            }
        }
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        layoutViewOnScroll(dx, recycler, state)
        offsetChildrenHorizontal(-dx)
        recyclerChildView(dx > 0, recycler)
        return dx
    }

    fun recyclerChildView(fillEnd: Boolean, recycler: RecyclerView.Recycler) {
        if (fillEnd) {
            var index = 0
            while (true) {
                var view: View? = getChildAt(index) ?: return
                val needRecycler = view?.right!! < paddingLeft
                if (needRecycler) {
                    removeAndRecycleView(view, recycler)
                } else {
                    return
                }
                index++
            }
        }else{
            var i = childCount - 1
            while (true) {
                val view = getChildAt(i) ?: return
                val needRecycler = view!!.left > width - paddingRight
                if (needRecycler) {
                    removeAndRecycleView(view, recycler)
                } else {
                    return
                }
                i--
            }
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                //滚动停止时
                //在这里我们可以去做一些位置便宜的功能
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                //拖拽滚动时
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
                //动画滚动时
            }
        }
    }
}