package com.example.androidx_branch.lighetoperate

import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import android.widget.Toast
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_lightoperate.*
import java.nio.channels.FileLock

/**
 * @Author Yu
 * @Date 2021/6/25 15:14
 * @Description viewgroup那些比较少的不为人知的api
 */
class LightOperateActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_lightoperate
    }

    private var mArcSlidingHelper: ArcSlidingHelper? = null

    override fun initView() {
        view1.post(Runnable {
            //创建对象
            mArcSlidingHelper = ArcSlidingHelper.create(
                view1
            ) { angle ->
                view1.rotation = view1.rotation + angle
            }
            //开启惯性滚动
            mArcSlidingHelper!!.enableInertialSliding(true)
            mArcSlidingHelper!!.setOnSlideFinishListener {
                Toast.makeText(
                    this, "finished", Toast.LENGTH_SHORT
                ).show()
            }
        })
        window.decorView.setOnTouchListener { v, event ->
            //处理滑动事件
            mArcSlidingHelper!!.handleMovement(event)
            true
        }
        view1.setOnClickListener {
            moveToTop(it)
        }
        view2.setOnClickListener {
            moveToTop(it)
        }
        view3.setOnClickListener {
            moveToTop(it)
        }
        view4.setOnClickListener {
            moveToTop(it)
        }
        getAnfleView.post {
            var centerPoint = arrayOfNulls<Float>(2)       //旋转中心点坐标
            centerPoint[0] = getAnfleView.measuredWidth / 2f
            centerPoint[1] = getAnfleView.measuredHeight / 2f
            getAnfleView.initParams(AngleGetUtil(Scroller(this,DecelerateInterpolator()), centerPoint) {
                imgHead.rotation = it + imgHead.rotation
            })
        }

    }

    private fun moveToTop(target: View) {
        var mViewGroup = conn
        //先确定现在在哪个位置
        val startIndex: Int = mViewGroup.indexOfChild(target)
        //计算一共需要几次交换，就可到达最上面
        val count: Int = mViewGroup.childCount - 1 - startIndex
        for (i in 0 until count) {
            //更新索引
            val fromIndex: Int = mViewGroup.indexOfChild(target)
            //目标是它的上层
            val toIndex = fromIndex + 1
            //获取需要交换位置的两个子View
            val to: View = mViewGroup.getChildAt(toIndex)

            //先把它们拿出来
            mViewGroup.detachViewFromParent(toIndex)
            mViewGroup.detachViewFromParent(fromIndex)

            //再放回去，但是放回去的位置(索引)互换了
            mViewGroup.attachViewToParent(to, fromIndex, to.layoutParams)
            mViewGroup.attachViewToParent(target, toIndex, target.layoutParams)
        }
        //刷新
        mViewGroup.invalidate()
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}