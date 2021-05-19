package com.example.androidx_branch.lianxiren.sortlist

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import java.text.FieldPosition
import kotlin.math.abs

/**
 * @Author Yu
 * @Date 2021/3/17 12:37
 * @Description TODO
 */
class SSTwo(val mContext: Context) : RecyclerView.LayoutManager() {
    var initLocationPosition = 0 //第一次可见的位置
    var targetScale = 0.7f//目标最终的缩放值
    var fansui = 0f
    var initViewVisibilityWidth = ViewUtil.dip2px(35f)//view初始时候露出来的那一丢丢
    var itemSpaceBetweenDistance = 0f//每个item间距
    var everyItemBetweenParentLeft = 0f//每一个子距离父亲的左边距
    var formerWidth = 0
    var laterWidth = 0
    var scaleAfterDistance = 0f//每个view缩放后导致的偏移量
    var allChildTotalWidth = 0f//所有view加起来的宽度

    var realItemBetweenDistance = 0f//实际真实情况下两个item间距
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    var method = true;
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        var s = dx
        onScrollLayoutView(recycler, dx)
        return s
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        detachAndScrapAttachedViews(recycler)
        if (itemCount > 0) {
            var childView = recycler.getViewForPosition(0)
            measureChildWithMargins(childView, 0, 0)
            formerWidth = childView.measuredWidth
            laterWidth = (childView.measuredWidth * targetScale).toInt()
            scaleAfterDistance = (formerWidth - laterWidth) / 2f

            itemSpaceBetweenDistance =
                (width - formerWidth - initViewVisibilityWidth * 2) / 2f
            everyItemBetweenParentLeft = itemSpaceBetweenDistance + initViewVisibilityWidth
        }
        allChildTotalWidth =
            (formerWidth + (laterWidth + itemSpaceBetweenDistance) * (itemCount - 1))
        fansui = formerWidth / laterWidth.toFloat()
        realItemBetweenDistance = abs((formerWidth - laterWidth) / 2 - itemSpaceBetweenDistance)
        firstInitView(recycler)
    }

    fun firstInitView(recycler: RecyclerView.Recycler) {
        for (index in 0 until itemCount) {
            var childView = recycler.getViewForPosition(index)
            addView(childView)
            measureChildWithMargins(childView, 0, 0)
            if (index != initLocationPosition) {
                //view的真实宽度不会随着缩放而变化
                childView.scaleY = targetScale
                childView.scaleX = targetScale
            }
            var left = 0

            left = when {
                index < initLocationPosition -> {
                    -(abs((initLocationPosition - index) * formerWidth) - initViewVisibilityWidth) + scaleAfterDistance.toInt()
                }
                index == initLocationPosition -> {
                    everyItemBetweenParentLeft.toInt()
                }
                else -> {
                    (formerWidth + (index - initLocationPosition - 1) * laterWidth + everyItemBetweenParentLeft + itemSpaceBetweenDistance * (index - initLocationPosition) - scaleAfterDistance.toInt()).toInt()
                }
            }

            var top = 0
            var right = left + formerWidth

            var bottom = childView.measuredHeight
            layoutDecoratedWithMargins(
                childView, left, top, right, bottom
            )
        }

    }

    var curentMax = 0

    /**
     * 屏幕滑动的时候 初始化view
     */
    fun onScrollLayoutView(recycler: RecyclerView.Recycler, dx: Int) {
        detachAndScrapAttachedViews(recycler)
        for (index in 0 until itemCount - 1) {
            var childView = recycler.getViewForPosition(index)
            addView(childView)
            measureChildWithMargins(childView, 0, 0)
            var childleft = childView.left
            var scale = getPositionScale(childleft, index, childView)
            var left = childView.left - dx
            if (method) {
                childView.scaleX = scale
                childView.scaleY = scale
            }


            var top = 0
            var right = left + formerWidth
            var bottom = childView.measuredHeight
            layoutDecoratedWithMargins(
                childView, left, top, right, bottom
            )
        }
    }

    /**
     * 获取对应位置的缩放值
     */
    fun getPositionScale(childLeft: Int, currentPosition: Int, view: View): Float {
        if (view.left > 0 && view.left < ViewUtil.getScreenWidth(mContext) || view.right > 0 && view.right < ViewUtil.getScreenWidth(
                mContext
            )
        ) {
            //表示在屏幕上
            if (view.left <= (width / 2f - formerWidth / 2f)) {
                var shouldOffDistance = width / 2f - initViewVisibilityWidth.toFloat()
                var scaled =
                    (shouldOffDistance * formerWidth).toFloat() / (formerWidth - laterWidth).toFloat()
                var scale = (scaled - (width / 2f - formerWidth / 2f - view.left)) / scaled
                return if (scale <= targetScale) {
                    targetScale
                } else {
                    scale
                }
            } else if (view.left > (width / 2f - formerWidth / 2f) && view.left < (width - initViewVisibilityWidth)) {
                var shouldOffDistance = width - initViewVisibilityWidth - initViewVisibilityWidth
                var scaled =
                    (shouldOffDistance * laterWidth).toFloat() / (formerWidth - laterWidth).toFloat()
                var scale =
                    (scaled - (width - initViewVisibilityWidth - view.left)) / scaled
                return if (scale <= targetScale) {
                    1.7f - targetScale
                } else {
                    1.7f - scale
                }
            } else {
                return targetScale
            }

        } else {
            return targetScale
        }

    }
}