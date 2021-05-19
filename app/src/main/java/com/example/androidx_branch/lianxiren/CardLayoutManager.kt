package com.example.androidx_branch.lianxiren

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import java.util.function.LongFunction

/**
 * @Author Yu
 * @Date 2021/3/16 9:49
 * @Description TODO
 */
class CardLayoutManager : RecyclerView.LayoutManager() {
    var itemWidth = 0 //一个item的宽度 包括空间的宽度和距离左边的间距
    lateinit var recyclerView: RecyclerView

    /**
     * 规律：
     *  每个item间的距离是固定的
     */
    var itemSpaceIng = 0f

    //两边可见的宽度
    var leftViewVisibilityWidth = ViewUtil.dip2px(15f)//两边view可见的宽度

    //缩放比例
    var mScale = 0.7f

    var firstInitViewCount = 5//刚开始初始化的view个数
    var smoothDistance = 0//总共滑动的距离

    var initialPosition = 0//初始可见位置

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        return layOut(recycler, state, (dx).toInt())
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        recyclerView = view!!
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        initialPosition = 1
        /**
         * 第一步回收复用
         */
        detachAndScrapAttachedViews(recycler)
        if (itemCount > 0) {
            var childView = recycler.getViewForPosition(0)
            measureChildWithMargins(childView, 0, 0)
            itemSpaceIng = (width - childView.measuredWidth - leftViewVisibilityWidth * 2) / 2f
            itemWidth = itemSpaceIng.toInt() + childView.measuredWidth
        }
        smoothDistance = (itemWidth - leftViewVisibilityWidth) * initialPosition -   ((itemWidth - itemWidth * mScale) / 2).toInt()
        layOutFirst(recycler, state)
    }


    //初始化布局 在第一布局的时候 只布局显示在界面上的view
    private fun layOutFirst(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        for (index in 0 until itemCount) {
            var childView = recycler.getViewForPosition(index)
            addView(childView)
            measureChildWithMargins(childView, 0, 0)
            var scale = mScale
            if (index != initialPosition) {
                childView.scaleY = scale
                childView.scaleX = scale
            }
            var left =
                (index) * (itemWidth) + itemSpaceIng.toInt() - (itemWidth - leftViewVisibilityWidth) * initialPosition

            if (index < initialPosition) {
                left += ((itemWidth - itemWidth * scale) / 2).toInt()
            } else if (index > initialPosition) {
                left -= ((itemWidth - itemWidth * scale) / 2).toInt()
            }

            var top = 0
            var right = left + childView.measuredWidth
            var bottom = height
            layoutDecoratedWithMargins(
                childView,
                left,
                top,
                right,
                bottom
            )
        }
    }

    override fun calculateItemDecorationsForChild(child: View, outRect: Rect) {
        super.calculateItemDecorationsForChild(child, outRect)
    }

    private fun layOut(recycler: RecyclerView.Recycler, state: RecyclerView.State, dx: Int): Int {
        var smd = 0
        smoothDistance += dx

        if (smoothDistance >= itemWidth * (itemCount - 1)) {
            smd = itemWidth * (itemCount - 1) - (smoothDistance - dx)
            smoothDistance = itemWidth * (itemCount - 1)
        } else {
            smd = dx
        }

        if (smoothDistance <= 0) {
            smd = 0
            smoothDistance = 0
        }


        detachAndScrapAttachedViews(recycler)//回收所有
        for (index in 0 until itemCount) {
            var childView = recycler.getViewForPosition(index)
            addView(childView)
            measureChildWithMargins(childView, 0, 0)

            var left = (childView.left) - smd
            if (smoothDistance == 0) {
                left =
                    (index) * (childView.measuredWidth + itemSpaceIng.toInt()) + itemSpaceIng.toInt()
            }
            var top = 0
            var right = left + childView.measuredWidth
            var bottom = height
            layoutDecoratedWithMargins(
                childView,
                left,
                top,
                right,
                bottom
            )
            var scale = scaleToPosition(index)
            childView.scaleY = scale
            childView.scaleX = scale
        }

        return smd
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                //滚动停止时
                //在这里我们可以去做一些位置便宜的功能
                Log.i("zjc", "状态1")

            }
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                //拖拽滚动时
                Log.i("zjc", "状态2")
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
                //动画滚动时
                Log.i("zjc", "状态3")
            }
        }
    }


    fun scaleToPosition(currentPosition: Int): Float {

        var currentPosition_2 = (smoothDistance + leftViewVisibilityWidth) / itemWidth //表示现在在第几个


        if (currentPosition <= currentPosition_2) {
            var offsetXDistance = currentPosition * itemWidth //当前position的长度
            var shengDistance = smoothDistance - offsetXDistance
            var scale = (itemWidth - shengDistance) / itemWidth.toFloat()
            return if (scale < mScale) {
                mScale
            } else if (scale > 1f) {
                1f
            } else {
                scale
            }
        } else if (currentPosition > currentPosition_2  ) {
            var offsetXDistance = currentPosition * itemWidth //当前position的长度
            var shengDistance = offsetXDistance - smoothDistance
            var scale = (itemWidth - shengDistance) / itemWidth.toFloat()

            return if (scale > 1f) {
                1f
            } else if (scale < mScale) {
                mScale
            } else {
                scale
            }
        }

        return 1f
    }

}