package com.example.androidx_branch.danmu

import android.animation.Animator
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activty_danmu.*
import kotlinx.android.synthetic.main.layout_itemdanmu.view.*
import java.util.*

/**
 * @Author Yu
 * @Date 2021/3/25 15:24
 * @Description 弹幕
 */
class DanMuView : FrameLayout {
    var queue: Queue<String> = LinkedList<String>()

    var mContext: Context? = null
    val animator: ObjectAnimator by lazy {
        var allDistance =
            ViewUtil.dip2px(204f).toFloat() + ViewUtil.getScreenWidth(mContext!!).toFloat()
        var leftDistance = (ViewUtil.dip2px(100f))
        var kongjianWidtd = ViewUtil.dip2px(204f).toFloat()
        var diyiduan = allDistance - (leftDistance + kongjianWidtd)
        var dierduan = diyiduan + leftDistance

        var diyiBai: Float = diyiduan / allDistance
        var dierBai: Float = dierduan / allDistance

        var keyFrame1 = Keyframe.ofFloat(0f, -0f)
        var keyFrame3 = Keyframe.ofFloat(0.2f, -(diyiBai * allDistance))
        var keyFrame4 = Keyframe.ofFloat(0.97f, -(dierBai * allDistance))
        var keyFrame5 = Keyframe.ofFloat(1f, -(1 * allDistance).toFloat())

        var keyFrameHolder =
            PropertyValuesHolder.ofKeyframe(
                "translationX",
                keyFrame1,
                keyFrame3,
                keyFrame4,
                keyFrame5
            )
        ObjectAnimator.ofPropertyValuesHolder(this, keyFrameHolder).apply {
            duration = 2000
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    Log.i("zjc", "onAnimationStart")
                    var item = queue.poll()
                    if (item != null) {
                        this@DanMuView.tv_yonghu.text = item
                    }
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Log.i("zjc", "onAnimationStart")
                    if (queue.size > 0) {
                        start()
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
        }
    }


    constructor(context: Context, attribute: AttributeSet) : super(context, attribute) {
        mContext = context
        queue.clear()
        var view = LayoutInflater.from(context).inflate(R.layout.layout_itemdanmu, this, false)
        addView(view)
    }

    fun startInRoomAnimal() {
        if (!animator.isRunning) {
            animator.start()
        }
    }


}