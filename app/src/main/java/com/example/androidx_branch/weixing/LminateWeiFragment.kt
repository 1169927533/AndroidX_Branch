package com.example.androidx_branch.weixing

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.androidx_branch.R
import com.example.androidx_branch.video.MyVideoView
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activity_weixing.*
import kotlin.math.abs

/**
 * @Author Yu
 * @Date 2021/3/8 9:36
 * @Description 仿照微信我的界面
 */
class LminateWeiFragment:FrameLayout {
    var offY = 0f
    var smoothDistance = 0f
    val screenWidth by  lazy {
        ViewUtil.getScreenHeight(context).toFloat()//屏幕宽度
    }
    lateinit var myVideoView:MyVideoView//背景view
    lateinit var childTargetView:ViewGroup//需要动态更新位置的view
    private var marginBottomDistance:Float =200f//距离底部的距离
    @AnimalTypeAnnotation
    var slideAnimalTYpe: String = "SLIDE_DOWN"

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var childCount = childCount
        if(childCount>=2){
            childTargetView = getChildAt(1) as ViewGroup
            myVideoView = getChildAt(0) as MyVideoView
        }
    }
    var downY:Float = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when(ev.action){
            MotionEvent.ACTION_DOWN->{
                downY = ev.rawY
                return false
            }
            MotionEvent.ACTION_MOVE->{
                return if(abs(ev.rawY - downY) >100){
                    offY =  ev.rawY - childTargetView.y * 4
                    true
                }else{
                    false
                }
            }
            MotionEvent.ACTION_UP->{
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                smoothDistance = event.rawY - offY
                childTargetView.y = smoothDistance / 4
                if (smoothDistance / 10 < 200) {
                    childTargetView.findViewById<ImageView>(R.id.img_head).alpha =  (200 - smoothDistance / 10) / 200f
                }

            }
            MotionEvent.ACTION_UP -> {
                var alpha = 0f
                if (slideAnimalTYpe == "SLIDE_UP") {
                    slideAnimalTYpe = "SLIDE_DOWN"
                    smoothDistance = -childTargetView.top.toFloat()
                    alpha = 1f
                } else {
                    slideAnimalTYpe = "SLIDE_UP"
                    alpha = 0f
                    smoothDistance =screenWidth - childTargetView.y- marginBottomDistance-1000
                }
                setAnimalToY(childTargetView, smoothDistance, alpha)
            }
            MotionEvent.ACTION_CANCEL -> {
            }
        }
        return true
    }
    //给y轴的偏移设置动画
    private fun setAnimalToY(targetView: View, yDistance: Float, alpha: Float) {
        childTargetView.findViewById<ImageView>(R.id.img_head).animate().alpha(alpha).duration = 500
        var animalTypeAnnotation = targetView.animate().translationY(yDistance)
        animalTypeAnnotation.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                showVideo("http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4")
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
        animalTypeAnnotation.duration = 500
    }
    private fun showVideo(videoPath: String) {
        if (myVideoView.isPlaying) {
            myVideoView.stop()
        } else {
            myVideoView.stop()
            myVideoView.setVideoPath(videoPath)
            myVideoView.start()
        }
    }
}