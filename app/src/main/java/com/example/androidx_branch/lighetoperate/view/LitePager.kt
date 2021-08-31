package com.example.androidx_branch.lighetoperate.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.example.a11699.lib_util.dp
import com.example.androidx_branch.lighetoperate.layoutparams.MyLayoutParams
import kotlin.math.abs

/**
 * @Author Yu
 * @Date 2021/7/1 13:55
 * @Description 仿照网易云的viewpager切换
 */
class LitePager : FrameLayout {
    private var assistPath = Path()
    private var paintStrokeWidth = 2f.dp

    private var mMinAlpha = 0.4f
    private var mMinScale = 0.8f
    private var mAnimator: ValueAnimator? = null

    private val assistPaint by lazy {
        Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = paintStrokeWidth
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        for (index in 0 until childCount) {
            var childView = getChildAt(index)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
        }
        setMeasuredDimension(widthMeasureSpec, 400f.dp.toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            //获取基准线
            val baseLine = getBaselineByChild(child)
            //布局子View
            layoutChild(child, baseLine)
        }
    }

    private fun getBaselineByChild(child: View): Int {
        val baseLineLeft = width / 4        //左边View的初始基准线
        val baseLineCenter = width / 2        //中间的
        val baseLineRight = width - baseLineLeft        //右边的
        var baseLine = 0
        val lp = child.layoutParams as MyLayoutParams
        //用出发点来作为条件，而不是当前索引，因为如果使用当前索引的话，在交换顺序之后，就不正确了
        when (lp.from) {
            //左边的子View
            0 -> baseLine = when (lp.to) {
                //目的地是1，证明手指正在向左滑动，所以下面的mOffsetPercent是用负的
                //当前基准线 = 初始基准线 + 与目标基准线(现在是右边)的距离 * 滑动百分比
                1 -> baseLineLeft + ((baseLineRight - baseLineLeft) * -mOffsetPercent).toInt()

                //如果目的地是中间(2)，那目标基准线就是ViewGroup宽度的一半了(baseLineCenter)，计算方法同上
                2 -> baseLineLeft + ((baseLineCenter - baseLineLeft) * mOffsetPercent).toInt()
                else -> baseLineLeft
            }
            //右边的子View
            1 -> baseLine = when (lp.to) {
                //原理同上
                0 -> baseLineRight + ((baseLineRight - baseLineLeft) * -mOffsetPercent).toInt()
                2 -> baseLineRight + ((baseLineRight - baseLineCenter) * mOffsetPercent).toInt()
                else -> baseLineRight
            }
            //中间的子View
            2 -> baseLine = when (lp.to) {
                //原理同上
                0 -> baseLineCenter + ((baseLineCenter - baseLineLeft) * mOffsetPercent).toInt()
                1 -> baseLineCenter + ((baseLineRight - baseLineCenter) * mOffsetPercent).toInt()
                else -> baseLineCenter
            }
        }
        return baseLine
    }

    private fun layoutChild(child: View, baseLine: Int) {
        val lpp = child.layoutParams as MyLayoutParams
        //更新不透明度
        child.alpha = lpp.alpha
        //更新缩放比例
        child.scaleX = lpp.scale
        child.scaleY = lpp.scale

        //获取子View测量宽高
        val childWidth = child.measuredWidth
        val childHeight = child.measuredHeight
        //垂直的中心位置，即高度的一半
        val baseLineCenterY = height / 2
        //根据基准线来定位水平上的位置
        val left = baseLine - childWidth / 2
        val right = left + childWidth
        //垂直居中
        val top = baseLineCenterY - childHeight / 2
        val bottom = top + childHeight

        val lp = child.layoutParams as LayoutParams

        child.layout(
            left + lp.leftMargin + paddingLeft,
            top + lp.topMargin + paddingTop,
            right + lp.leftMargin - paddingRight,
            bottom + lp.topMargin - paddingBottom
        )
    }

    var mLastX = 0f
    var mLastY = 0f
    private var mDownX = 0f
    private var mDownY = 0f
    private var mOffsetX = 0f               // 总共的偏移量
    private var mOffsetPercent = 0f         // 滑动的距离占据viewGroup的百分比
    private var mTouchSlop = 10f.dp         // 滑动多长距离才算滑动
    private var isBeginDragged = false      // 是否开始拖动
    private var isReordered = false         // 标记是否交换过
    private val mFlingDuration: Long = 400L // 动画执行时长

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { event ->
            var x = event.x
            var y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 更新上一次点击的位置
                    mLastX = event.x
                    mLastY = event.y
                    mDownX = mLastX
                    mDownY = mLastY
                    //开始滑动前先打断动画
                    abortAnimation()
                }
                MotionEvent.ACTION_MOVE -> {
                    val offsetX = x - mLastX
                    val offsetY = y - mLastY
                    // 判断是否触发拖动事件
                    if (abs(offsetX) > mTouchSlop || abs(offsetY) > mTouchSlop) {
                        // 更新上一次触摸值
                        mLastX = x
                        mLastY = y
                        isBeginDragged = true
                    } else {

                    }
                }
                MotionEvent.ACTION_UP -> {
                    // 拖动停止
                    isBeginDragged = false
                    handleActionUp(x, y)
                }
                else -> {

                }
            }
        }
        return isBeginDragged
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { ev ->
            val x = ev.x
            val y = ev.y
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    //开始滑动前先打断动画
                    abortAnimation()
                }
                MotionEvent.ACTION_MOVE -> {
                    val offsetX = x - mLastX
                    mOffsetX += offsetX
                    onItemMove()
                }
                MotionEvent.ACTION_UP -> {
                    isBeginDragged = false
                    handleActionUp(x, y)
                }
            }
            mLastX = x
            mLastY = y
        }
        return true
    }

    private fun handleActionUp(x: Float, y: Float): Boolean {
        val offsetX = x - mDownX
        val offsetY = y - mDownY
        //判断是否点击手势
        if (abs(offsetX) < mTouchSlop && Math.abs(offsetY) < mTouchSlop) {
            //查找被点击的子View
            val hitView = findHitView(x, y)
            if (hitView != null) {
                return if (indexOfChild(hitView) == 2) {
                    //点击第一个子view不用播放动画，直接不拦截
                    false
                } else {
                    val lp = hitView.layoutParams as MyLayoutParams
                    setSelection(lp.from)
                    //拦截ACTION_UP事件，内部消费
                    true
                }
            }
        }
        //手指在空白地方松开
        playFixingAnimation()
        return false
    }

    private fun findHitView(x: Float, y: Float): View? {
        //从顶层的子View开始递减遍历
        for (index in childCount - 1 downTo 0) {
            val child = getChildAt(index)
            //判断触摸点是否在这个子View内
            if (pointInView(child, floatArrayOf(x, y))) {
                //如果在就直接返回它
                return child
            }
        }
        //没有找到，返回null
        return null
    }

    // 判断点击的点是不是在view李里面
    private fun pointInView(view: View, points: FloatArray): Boolean {
        points[0] -= view.left.toFloat()
        points[1] -= view.top.toFloat()
        val matrix = view.matrix
        if (!matrix.isIdentity) {
            matrix.invert(matrix)
            matrix.mapPoints(points)
        }
        return points[0] >= 0.0f && points[1] >= 0.0f && points[0] < view.width.toFloat() && points[1] < view.height
            .toFloat()
    }

    fun setSelection(index: Int) {
        //目标index已被选中、无子View、正在播放动画，这几种情况下，都直接忽略，即不播放本次动画
        if (indexOfChild(getChildAt(childCount - 1)) == index || childCount == 0 || mAnimator?.isRunning == true) {
            return
        }
        //起始点就是当前滑动距离
        val start = mOffsetX
        //结束点
        val end = when (index) {
            //如果要选中0，即左边的子View，那么就要向右移动
            0 -> width.toFloat()
            //反之，如果是1(右边的子View)，则向左移动，也就是负数了
            1 -> -width.toFloat()
            else -> return
        }
        //开始播放动画
        startValueAnimator(start, end)
    }

    private fun playFixingAnimation() {
        //没有子View还播放什么动画，出去
        if (childCount == 0) {
            return
        }
        //起始点，就是当前的滑动距离
        val start = mOffsetX
        //结束点
        val end = when {
            //如果滑动的距离超过了宽度的一半，那么就顺势往那边走下去
            //如果滑动百分比是正数，表示是向右滑了>50%，所以目的地就是宽度
            mOffsetPercent > .5F -> width.toFloat()
            //相反，如果是负数，那就拿负的宽度
            mOffsetPercent < -.5F -> -width.toFloat()
            //如果滑动没超过50%，那就把距离变成0，也就是回退了
            else -> 0F
        }
        startValueAnimator(start, end)
    }

    private fun startValueAnimator(start: Float, end: Float) {
        if (start == end) {
            //起始点和结束点一样，那还播放什么动画，出去
            return
        }
        //先打断之前的动画，如果正在播放的话
        abortAnimation()
        //创建动画对象
        mAnimator = with(ValueAnimator.ofFloat(start, end)) {
            //指定动画时长
            duration = mFlingDuration
            //监听动画更新
            addUpdateListener { animation ->
                val currentValue = animation.animatedValue as Float
                //更新滑动距离
                mOffsetX = currentValue
                //处理子View的移动行为
                onItemMove()
            }
            //开始动画
            start()
            this
        }
    }

    private fun abortAnimation() {
        mAnimator?.let { if (it.isRunning) it.cancel() }
    }

    private fun onItemMove() {
        mOffsetPercent = mOffsetX / width
        updateChildrenFromAndTo()
        //更新子View的层级顺序
        updateChildrenOrder()
        updateChildrenAlphaAndScale()
        requestLayout()
    }

    private fun updateChildrenFromAndTo() {
        //如果滑动的距离>=ViewGroup宽度
        if (abs(mOffsetPercent) >= 1) {
            //在这里要重置一下标记
            isReordered = false
            //遍历子View，标记已经到达目的地
            for (i in 0 until childCount) {
                val lp = getChildAt(i).layoutParams as MyLayoutParams
                lp.from = lp.to
            }
            //处理溢出: 比如总宽度是100，现在是120，那么处理之后会变成20
            mOffsetX %= width.toFloat()
            //同理，这个是百分比
            mOffsetPercent %= 1F
        } else {
            //遍历子View，并根据当前滑动的百分比来更新子View的目的地
            for (i in 0 until childCount) {
                val lp = getChildAt(i).layoutParams as MyLayoutParams
                lp.to = when (lp.from) {
                    //最左边的子View，如果是向右滑动的话，那么它的目的地是中间，也就是2了
                    //如果是向左滑动的话，目的地是最右边的位置，也是1了，下面同理
                    0 -> if (mOffsetPercent > 0) 2 else 1
                    //最右边的子View，如果是向右滑动，那么目的地就是最左边(0)，反之，在中间(2)
                    1 -> if (mOffsetPercent > 0) 0 else 2
                    //中间的子View，如果向右滑动，目的地是右边(1)，向左就是左边(0)
                    2 -> if (mOffsetPercent > 0) 1 else 0
                    else -> return
                }
            }
        }
    }

    private fun updateChildrenOrder() {
        //如果滑动距离超过了ViewGroup宽度的一半，
        //就把索引为1，2的子View交换顺序，并标记已经交换过
        if (Math.abs(mOffsetPercent) > .5F) {
            if (!isReordered) {
                exchangeOrder(1, 2)
                isReordered = true
            }
        } else {
            //滑动距离没有超过宽度一半，即有可能是滑动超过一半然后滑动回来
            //如果isReordered=true，就表示本次滑动已经交换过顺序
            //所以要再次交换一下
            if (isReordered) {
                exchangeOrder(1, 2)
                isReordered = false
            }
        }
    }

    private fun exchangeOrder(fromIndex: Int, toIndex: Int) {
        //一样的就不用换了
        if (fromIndex == toIndex) {
            return
        }
        //先获取引用
        val from = getChildAt(fromIndex)
        val to = getChildAt(toIndex)

        //分离出来
        detachViewFromParent(from)
        detachViewFromParent(to)

        //重新放回去，但是index互换了
        attachViewToParent(
            from,
            if (toIndex > childCount) childCount else toIndex,
            from.layoutParams
        )
        attachViewToParent(
            to,
            if (fromIndex > childCount) childCount else fromIndex,
            to.layoutParams
        )

        //通知重绘，刷新视图
        invalidate()
    }

    private fun updateChildrenAlphaAndScale() {
        //遍历子View
        for (i in 0 until childCount) {
            updateAlphaAndScale(getChildAt(i))
        }
    }

    private fun updateAlphaAndScale(child: View) {
        val lp = child.layoutParams as MyLayoutParams
        //用出发点来作为条件，而不是当前索引，因为如果使用当前索引的话，在交换顺序之后，就不正确了
        when (lp.from) {
            //最左边的子View
            0 -> when (lp.to) {
                //如果它目的地是最右边的话
                1 -> {
                    //要把它放在最底，为了避免在移动过程中遮挡其他子View
                    setAsBottom(child)
                    //透明度和缩放比例都不用变，因为现在就已经满足条件了
                }
                //如果它要移动到中间
                2 -> {
                    //根据滑动比例来计算出当前的透明度和缩放比例
                    lp.alpha = mMinAlpha + (1F - mMinAlpha) * mOffsetPercent
                    lp.scale = mMinScale + (1F - mMinScale) * mOffsetPercent
                }
            }
            //最右边的子View
            1 -> when (lp.to) {
                0 -> {
                    //把它放在最底，避免在移动过程中遮挡其他子View
                    setAsBottom(child)
                    //透明度和缩放比例都不用变
                }
                2 -> {
                    //这里跟上面唯一不同的地方就是mOffsetPercent要取负的
                    //因为它向中间移动的时候，mOffsetPercent是负数，这样做就刚好抵消
                    lp.alpha = mMinAlpha + (1F - mMinAlpha) * -mOffsetPercent
                    lp.scale = mMinScale + (1F - mMinScale) * -mOffsetPercent
                }
            }
            //中间的子View
            2 -> {
                //这里不用判断to了，因为无论向哪一边滑动，不透明度和缩放比例都是减少
                lp.alpha = 1F - (1F - mMinAlpha) * Math.abs(mOffsetPercent)
                lp.scale = 1F - (1F - mMinScale) * Math.abs(mOffsetPercent)
            }
        }
    }

    private fun setAsBottom(child: View) {
        //获取child索引后跟0交换层级顺序
        exchangeOrder(indexOfChild(child), 0)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let { mCanvas ->
            assistPath.moveTo(paintStrokeWidth / 2f, paintStrokeWidth / 2f)
            assistPath.lineTo(
                measuredWidth.toFloat() - paintStrokeWidth / 2f,
                0f + paintStrokeWidth / 2f
            )
            assistPath.lineTo(
                measuredWidth.toFloat() - paintStrokeWidth / 2f,
                measuredHeight.toFloat() - paintStrokeWidth / 2f
            )
            assistPath.lineTo(
                paintStrokeWidth / 2f,
                measuredHeight.toFloat() - paintStrokeWidth / 2f
            )
            assistPath.close()
            var w = measuredWidth
            var itemWidth = w / 4
            assistPath.moveTo(itemWidth.toFloat(), 0f)
            assistPath.lineTo(itemWidth.toFloat(), measuredHeight.toFloat() - paintStrokeWidth / 2f)
            assistPath.moveTo(itemWidth.toFloat() * 2, 0f)
            assistPath.lineTo(
                itemWidth.toFloat() * 2,
                measuredHeight.toFloat() - paintStrokeWidth / 2f
            )
            assistPath.moveTo(itemWidth.toFloat() * 3, 0f)
            assistPath.lineTo(
                itemWidth.toFloat() * 3,
                measuredHeight.toFloat() - paintStrokeWidth / 2f
            )
            mCanvas.drawPath(assistPath, assistPaint)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet) = MyLayoutParams(context, attrs)

    override fun generateLayoutParams(p: ViewGroup.LayoutParams) = MyLayoutParams(p)

    override fun generateDefaultLayoutParams() = MyLayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (childCount > 2) {
            //满座了就直接拋异常，提示不能超过三个子View
            throw IllegalStateException("LitePager can only contain 3 child!")
        }
        //如果传进来的LayoutParams不是我们自定义的LayoutParams的话，就要创建一个
        val lp = if (params is MyLayoutParams) params else MyLayoutParams(params)
        lp.from = if (index == -1) childCount else index
        if (childCount < 2) {
            lp.alpha = mMinAlpha
            lp.scale = mMinScale
        } else {
            lp.alpha = 1F
            lp.scale = 1F
        }
        super.addView(child, index, params)
    }

}